package model.entity;

import model.entity.impl.Animal;
import model.entity.impl.Grass;

public enum EntityType {
    ANIMAL(Animal.class),
    GRASS(Grass.class);

    private final Class<? extends Entity> klass;

    private EntityType(Class<? extends Entity> klass) {
        this.klass = klass;
    }

}
