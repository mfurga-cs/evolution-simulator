package model.entity;

import model.utils.Vector2D;

public interface EntityObserver {

    void onPositionChange(Entity entity, Vector2D oldPosition, Vector2D newPosition);

}
