package model.entity.impl;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.entity.Direction;
import model.genotype.Genotype;
import model.utils.Vector2D;
import model.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class Animal extends BaseEntity {

    private Direction direction;
    private Genotype genotype;

    public Animal(World world, int energy) {
        this(world, energy, new Vector2D(ThreadLocalRandom.current().nextInt(0, world.getWidth()),
                                         ThreadLocalRandom.current().nextInt(0, world.getHeight())));
    }

    public Animal(World world, int energy, Vector2D position) {
        this(world, energy, position, Genotype.random());
    }

    public Animal(World world, int energy, Vector2D position, Genotype genotype) {
        this(world, energy, position, genotype, Direction.random());
    }

    public Animal(World world, int energy, Vector2D position, Genotype genotype, Direction direction) {
        super(world, position, energy);
        this.direction = direction;
        this.genotype = genotype;
    }

    public void move() {
        Direction direction = Direction.fromId(
                this.genotype.getAt(ThreadLocalRandom.current().nextInt(0, Genotype.GENOTYPE_LENGTH)));
        move(direction);
    }

    private void move(Direction direction) {
        Vector2D unit = direction.toUnitVector();
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

    public Direction getDirection() {
        return this.direction;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    @Override
    public Paint getColor() {
        if (this.energy < 50) {
            return Color.rgb(217, 178, 37);
        }

        if (this.energy < 200) {
            return Color.rgb(184, 150, 28);
        }

        return Color.rgb(145, 120, 29);
    }
}
