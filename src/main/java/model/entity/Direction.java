package model.entity;

import model.utils.Vector2D;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    NORTH(0, new Vector2D(0, 1)),
    NORTHEAST(1, new Vector2D(1, 1)),
    EAST(2, new Vector2D(1, 0)),
    SOUTHEAST(3, new Vector2D(1, -1)),
    SOUTH(4, new Vector2D(0, -1)),
    SOUTHWEST(5, new Vector2D(-1, -1)),
    WEST(6, new Vector2D(-1, 0)),
    NORTHWEST(7, new Vector2D(-1, 1));

    private final int id;
    private final Vector2D unitVector;

    private Direction(int numeric, Vector2D vector) {
        this.id = numeric;
        this.unitVector = vector;
    }

    public Vector2D toUnitVector() {
        return this.unitVector;
    }

    public static Direction random() {
        return Direction.fromId(ThreadLocalRandom.current().nextInt(0, 8));
    }

    public static Direction fromId(int id) {
        return Arrays.stream(values())
                .filter(d -> d.id == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Direction with id " + id + " doest not exist."));
    }
}
