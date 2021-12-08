package model.world.impl;

import model.utils.Vector2D;

public class BoundaryWord extends BaseWorld {

    public BoundaryWord() {
        super();
    }

    @Override
    public Vector2D positionNormalize(Vector2D position) {
        return position; /* Do nothing */
    }
}
