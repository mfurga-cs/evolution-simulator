package model.world;

import model.entity.Entity;
import model.entity.impl.Animal;
import model.entity.impl.Grass;
import model.utils.Vector2D;

import java.util.List;
import java.util.stream.Collectors;

public class WorldDrawer {
    private static final String EMPTY_CELL = " ";
    private static final String FRAME_SEGMENT = "-";
    private static final String CELL_SEGMENT = "|";
    private World map;

    private final List<Entity> entities;

    public WorldDrawer(World map) {
        this.map = map;
        this.entities = map.getAllEntities();
    }

    public String draw(Vector2D lowerLeft, Vector2D upperRight) {
        StringBuilder builder = new StringBuilder();
        for (int i = upperRight.getY() + 1; i >= lowerLeft.getY() - 1; i--) {
            if (i == upperRight.getY() + 1) {
                builder.append(drawHeader(lowerLeft, upperRight));
            }
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.getX(); j <= upperRight.getX() + 1; j++) {
                if (i < lowerLeft.getY() || i > upperRight.getY()) {
                    builder.append(drawFrame(j <= upperRight.getX()));
                } else {
                    builder.append(CELL_SEGMENT);
                    if (j <= upperRight.getX()) {
                        builder.append(drawObject(new Vector2D(j, i)));
                    }
                }
            }
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String drawFrame(boolean innerSegment) {
        if (innerSegment) {
            return FRAME_SEGMENT + FRAME_SEGMENT;
        } else {
            return FRAME_SEGMENT;
        }
    }

    private String drawHeader(Vector2D lowerLeft, Vector2D upperRight) {
        StringBuilder builder = new StringBuilder();
        builder.append(" y\\x ");
        for (int j = lowerLeft.getX(); j < upperRight.getX() + 1; j++) {
            builder.append(String.format("%2d", j));
        }
        builder.append(System.lineSeparator());
        return builder.toString();
    }

    private String drawObject(Vector2D currentPosition) {
        String result = null;

        int animalSize = this.entities
                .stream()
                .filter(e -> e.getPosition().equals(currentPosition))
                .filter(e -> e.getClass().equals(Animal.class))
                .collect(Collectors.toList()).size();

        int grassSize = this.entities
                .stream()
                .filter(e -> e.getPosition().equals(currentPosition))
                .filter(e -> e.getClass().equals(Grass.class))
                .collect(Collectors.toList()).size();

        if (animalSize == 0 && grassSize == 0) {
            return EMPTY_CELL;
        }

        if (animalSize == 0) {
            return "G";
        }

        return String.valueOf(animalSize);
    }
}
