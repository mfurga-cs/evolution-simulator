package model.entity.impl;

import model.entity.Direction;
import model.genotype.Genotype;
import model.utils.Logger;
import model.utils.Vector2D;
import model.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class Animal extends BaseEntity {

    private static final int ENTITY_PRIORITY = 1;
    private static final int DEFAULT_ENERGY = 500;

    private Direction direction;
    private Genotype genotype;

    public Animal(World world) {
        this(world, new Vector2D(ThreadLocalRandom.current().nextInt(0, world.getWidth()),
                                 ThreadLocalRandom.current().nextInt(0, world.getHeight())));
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

    public void moveOrRotate() {
        if (this.direction.equals(Direction.NORTH) || this.direction.equals(Direction.SOUTH)) {
            move();
        } else {
            rotate();
        }
    }

    private void rotate() {
        Logger.debug("Animal is rotating");
        this.direction = Direction.fromId(ThreadLocalRandom.current().nextInt(0, Genotype.GENOTYPE_LENGTH));
    }

    private void move() {
        Vector2D unit = this.direction.toUnitVector();
        Vector2D newPosition = this.world.positionNormalize(Vector2D.add(this.position, unit));
        if (this.world.canMoveTo(newPosition)) {
            Logger.debug("Animal is moving from " + this.position + " to " + newPosition);
            this.setPosition(newPosition);
        } else {
            Logger.debug("Animal cannot move from " + this.position + " to " + newPosition);
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

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "A";
    }
}
