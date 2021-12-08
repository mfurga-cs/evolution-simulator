package model.entity.impl;

import model.utils.Vector2D;
import model.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class Grass extends BaseEntity {

    private static final int ENTITY_PRIORITY = 0;
    private static final int DEFAULT_ENERGY = 500;

    public Grass(World world) {
        this(world, new Vector2D(ThreadLocalRandom.current().nextInt(0, world.getWidth()),
                                 ThreadLocalRandom.current().nextInt(0, world.getHeight())));
    }

    public Grass(World world, Vector2D position) {
        super(world, position, DEFAULT_ENERGY);
    }

    @Override
    public String toString() {
        return "G";
    }
}
