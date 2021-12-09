package model.simulation;

import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.genotype.Genotype;
import model.utils.Logger;
import model.utils.Vector2D;
import model.world.Section;
import model.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class Simulation implements Engine {

    private final World world;

    public Simulation(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        for (int i = 0; i < 40; i++) {
            this.world.place(new Animal(this.world));
        }

        for (int i = 0; i < 30; i++) {
            this.world.place(new Grass(this.world));
        }

        System.out.println(this.world);
        nextPeriod();
        System.out.println(this.world);
        nextPeriod();
        System.out.println(this.world);
    }

    private void nextPeriod() {
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

        if (junglePositions.size() > 0) {
            this.world.place(new Grass(this.world, junglePositions.get(0)));
        }

        if (steppePositions.size() > 0) {
            this.world.place(new Grass(this.world, steppePositions.get(0)));
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
                .forEach(a -> a.reduceEnergy(10));
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
            this.world.place(new Animal(this.world, position, genotype, energy));
            Logger.debug("Rozmnażanie!!!");
        }
    }
}
