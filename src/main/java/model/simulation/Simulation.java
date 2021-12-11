package model.simulation;

import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.genotype.Genotype;
import model.utils.Vector2D;
import model.world.Section;
import model.world.World;
import model.world.impl.BoundaryWord;
import model.world.impl.InfiniteWorld;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Simulation implements Engine {

    private final SimulationConfig config;
    private final World world;
    private int day = 0;

    public Simulation(SimulationConfig config, Class<? extends World> world) {
        this.config = config;
        if (world == BoundaryWord.class) {
            this.world = new BoundaryWord(config.mapWidth, config.mapHeight, config.jungleRatio);
        } else {
            this.world = new InfiniteWorld(config.mapWidth, config.mapHeight, config.jungleRatio);
        }
        this.init();
    }

    public World getWorld() {
        return this.world;
    }

    private void init() {
        this.initGrass();
        this.initAnimals();
    }

    @Override
    public void run() {
        this.initGrass();
        this.initAnimals();

        for (int i = 0; i < 10; i++) {
            nextPeriod();
        }
    }

    private void initAnimals() {
        for (int i = 0; i < this.config.startAnimals; i++) {
            this.world.place(new Animal(this.world, this.config.initialAnimalEnergy));
        }
    }

    private void initGrass() {
        List<Vector2D> junglePositions = this.world.getFreePositionsBySection(Section.JUNGLE);
        List<Vector2D> steppePositions = this.world.getFreePositionsBySection(Section.STEPPE);

        Collections.shuffle(junglePositions);
        Collections.shuffle(steppePositions);

        int maxJungle = 3 * this.config.startGrass / 4;
        int maxSteppe = this.config.startGrass / 4;

        for (Vector2D junglePosition: junglePositions) {
            maxJungle--;
            if (maxJungle < 0) break;
            this.world.place(new Grass(this.world, this.config.initialGrassEnergy, junglePosition));
        }

        for (Vector2D steppePosition: steppePositions) {
            maxSteppe--;
            if (maxSteppe < 0) break;
            this.world.place(new Grass(this.world, this.config.initialGrassEnergy, steppePosition));
        }
    }

    public void nextPeriod() {
        this.day++;
        this.growGrass();
        this.moveAnimals();
        this.eatGrassByAnimals();
        this.decreaseAnimalsEnergy();
        this.removeDeadAnimals();
        this.breedAnimals();
    }

    private void growGrass() {
        List<Vector2D> junglePositions = this.world.getFreePositionsBySection(Section.JUNGLE);
        List<Vector2D> steppePositions = this.world.getFreePositionsBySection(Section.STEPPE);

        Collections.shuffle(junglePositions);
        Collections.shuffle(steppePositions);

        if (junglePositions.size() > 0) {
            this.world.place(new Grass(this.world, this.config.initialGrassEnergy, junglePositions.get(0)));
        }

        if (steppePositions.size() > 0) {
            this.world.place(new Grass(this.world, this.config.initialGrassEnergy, steppePositions.get(0)));
        }
    }

    private void moveAnimals() {
        this.world.getEntitiesByType(Animal.class)
                .forEach(Animal::move);
    }

    private void eatGrassByAnimals() {
        List<Grass> grasses = this.world.getEntitiesByType(Grass.class);
        for (Grass grass: grasses) {
            List<Animal> animals = this.world.getEntitiesByTypeAndPosition(Animal.class, grass.getPosition());
            if (animals.size() == 0) {
                continue;
            }

            for (Animal animal: animals) {
                animal.addEnergy(grass.getEnergy() / animals.size());
            }
            this.world.remove(grass);
        }
    }

    private void decreaseAnimalsEnergy() {
        this.world.getEntitiesByType(Animal.class)
                .forEach(a -> a.reduceEnergy(this.config.moveEnergy));
    }

    private void removeDeadAnimals() {
        this.world.getEntitiesByType(Animal.class)
                .stream()
                .filter(a -> !a.hasEnergy())
                .forEach(this.world::remove);
    }

    private void breedAnimals() {
        List<Vector2D> positions = this.world.getEntitiesByType(Animal.class)
                .stream()
                .map(a -> a.getPosition())
                .distinct()
                .collect(Collectors.toList());

        for (Vector2D position: positions) {
            List<Animal> animalsInPosition = this.world.getEntitiesByTypeAndPosition(Animal.class, position);
            animalsInPosition.sort((a, b) -> b.getEnergy() - a.getEnergy());

            if (animalsInPosition.size() < 2) {
                continue;
            }

            Animal animal1 = animalsInPosition.get(0);
            Animal animal2 = animalsInPosition.get(1);

            int energy = animal1.getEnergy() / 4 + animal2.getEnergy() / 4;

            int ratio = Genotype.GENOTYPE_LENGTH * animal1.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());
            Genotype genotype = Genotype.mix(animal1.getGenotype(), animal2.getGenotype(), ratio);
            this.world.place(new Animal(this.world, energy, position, genotype));
        }
    }

    public int getDay() {
        return this.day;
    }
}
