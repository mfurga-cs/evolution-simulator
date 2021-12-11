package model.world.impl;

import model.utils.Vector2D;

public class InfiniteWorld extends BaseWorld {
    public InfiniteWorld(int width, int height, int jungleRatio) {
        super(width, height, jungleRatio);
    }

    @Override
    public Vector2D positionNormalize(Vector2D position) {
        return new Vector2D(Math.floorMod(position.getX(), this.width),
                            Math.floorMod(position.getY(), this.height));
    }
}
