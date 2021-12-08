package model.entity.impl;

import model.entity.Entity;
import model.entity.EntityObserver;
import model.utils.Vector2D;
import model.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntity implements Entity {

    protected final List<EntityObserver> observers = new ArrayList<>();

    protected World world;
    protected Vector2D position;
    protected int energy;

    public BaseEntity(World world, Vector2D position, int energy) {
        this.world = world;
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public void addObserver(EntityObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(EntityObserver observer) {
        this.observers.remove(observer);
    }

    protected void setPosition(Vector2D position) {
        Vector2D oldPosition = this.position;
        this.position = position;
        for (EntityObserver observer: this.observers) {
            observer.onPositionChange(this, oldPosition, position);
        }
    }

    @Override
    public String toString() {
        return "E";
    }
}
