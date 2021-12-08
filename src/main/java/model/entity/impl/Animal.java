package model.entity.impl;

import model.entity.Direction;
import model.genotype.Genotype;
import model.utils.Vector2D;
import model.world.World;

public class Animal extends BaseEntity {

    private static final int ENTITY_PRIORITY = 1;
    private static final int DEFAULT_ENERGY = 500;

    private Direction direction;
    private Genotype genotype;

    public Animal(World world) {
        this(world, new Vector2D(0, 0));
    }

    public Animal(World world, Vector2D position) {
        this(world, position, Genotype.random());
    }

    public Animal(World world, Vector2D position, Genotype genotype) {
        this(world, position, genotype, Direction.random());
    }

    public Animal(World world, Vector2D position, Genotype genotype, Direction direction) {
        super(world, position, DEFAULT_ENERGY);
        this.direction = direction;
        this.genotype = genotype;
    }

    public void move() {
        Vector2D unit = this.direction.toUnitVector();
        Vector2D newPosition = this.world.positionNormalize(Vector2D.add(this.position, unit));
        if (this.world.canMoveTo(newPosition)) {
            this.setPosition(newPosition);
        }
    }

    public boolean hasEnergy() {
        return this.energy > 0;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void reduceEnergy(int energy) {
        this.energy = Math.max(this.energy - energy, 0);
    }

    @Override
    public int getPriority() {
        return Animal.ENTITY_PRIORITY;
    }
}
