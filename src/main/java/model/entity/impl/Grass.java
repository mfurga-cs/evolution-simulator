package model.entity.impl;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.utils.Vector2D;
import model.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class Grass extends BaseEntity {

    public Grass(World world, int energy) {
        this(world, energy, new Vector2D(ThreadLocalRandom.current().nextInt(0, world.getWidth()),
                                         ThreadLocalRandom.current().nextInt(0, world.getHeight())));
    }

    public Grass(World world, int energy, Vector2D position) {
        super(world, position, energy);
    }

    @Override
    public Paint getColor() {
        return Color.rgb(71, 209, 13);
    }
}
