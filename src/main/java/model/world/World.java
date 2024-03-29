package model.world;

import model.entity.Entity;
import model.utils.Vector2D;

import java.util.List;

public interface World {

    List<Entity> getAllEntities();
    List<Entity> getEntitiesByPosition(Vector2D position);
    <T extends Entity> List<T> getEntitiesByType(Class<T> klass);
    <T extends Entity> List<T> getEntitiesByTypeAndPosition(Class<T> klass, Vector2D position);

    List<Vector2D> getFreePositionsBySection(Section section);

    public boolean place(Entity entity);
    public void remove(Entity entity);

    public boolean canMoveTo(Vector2D position);
    public Vector2D positionNormalize(Vector2D position);

    int getWidth();
    int getHeight();
}
