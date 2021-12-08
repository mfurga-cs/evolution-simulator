package model.entity.impl;

import model.utils.Vector2D;
import model.world.World;

public class Grass extends BaseEntity {

    private static final int ENTITY_PRIORITY = 0;
    private static final int DEFAULT_ENERGY = 500;

    public Grass(World world) {
        this(world, new Vector2D(0, 0));
    }

    public Grass(World world, Vector2D position) {
        super(world, position, DEFAULT_ENERGY);
    }

    @Override
    public int getPriority() {
        return Grass.ENTITY_PRIORITY;
    }
}
