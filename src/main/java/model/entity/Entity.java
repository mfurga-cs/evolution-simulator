package model.entity;

import model.utils.Vector2D;

public interface Entity {

    Vector2D getPosition();

    int getEnergy();

    void addObserver(EntityObserver observer);
    void removeObserver(EntityObserver observer);

}
