package model.world.impl;

import model.entity.Entity;
import model.entity.EntityObserver;
import model.utils.Vector2D;
import model.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseWorld implements World, EntityObserver {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    protected int width;
    protected int height;

    protected Map<Vector2D, List<Entity>> entities = new HashMap<>();

    public BaseWorld() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Vector2D position = new Vector2D(x, y);
                this.entities.put(position, new ArrayList<>());
            }
        }
    }

    @Override
    public List<Entity> getAllEntities() {
        return this.entities
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends Entity> List<T> getEntitiesByType(Class<T> klass) {
        return this.entities
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(e -> e.getClass().equals(klass))
                .map(e -> (T) e)
                .collect(Collectors.toList());
    }

    @Override
    public List<Entity> getEntitiesByPosition(Vector2D position) {
        return this.entities
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(e -> e.getPosition().equals(position))
                .collect(Collectors.toList());
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return position.getX() >= 0 && position.getX() < this.width &&
               position.getY() >= 0 && position.getY() < this.height;
    }

    @Override
    public void place(Entity entity) {
//        if (this.getEntitiesByPosition(entity.getPosition())
//                .stream()
//                .anyMatch(e -> e.getPriority() > entity.getPriority())) {
//            return false;
//        }
        this.entities.get(entity.getPosition()).add(entity);
        entity.addObserver(this);
    }

    @Override
    public void remove(Entity entity) {
        this.entities.get(entity.getPosition()).remove(entity);
        entity.removeObserver(this);
    }

    @Override
    public void onPositionChange(Entity entity, Vector2D oldPosition, Vector2D newPosition) {
        this.entities.get(oldPosition).remove(entity);
        this.entities.get(newPosition).add(entity);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
