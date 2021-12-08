package model.simulation;

import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.world.World;

public class Simulation implements Engine {

    private final World world;

    public Simulation(World world) {
        this.world = world;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            this.world.place(new Animal(this.world));
        }

        for (int i = 0; i < 20; i++) {
            this.world.place(new Grass(this.world));
        }

        System.out.println(this.world);
    }

    private void nextPeriod() {

        this.makeMoveOrRotateAnimals();

        this.removeDeadAnimals();
    }

    private void removeDeadAnimals() {
        this.world.getEntitiesByType(Animal.class)
                .stream()
                .filter(a -> !a.hasEnergy())
                .forEach(this.world::remove);
    }

    private void makeMoveOrRotateAnimals() {
        this.world.getEntitiesByType(Animal.class)
                .forEach(Animal::moveOrRotate);
    }

}
