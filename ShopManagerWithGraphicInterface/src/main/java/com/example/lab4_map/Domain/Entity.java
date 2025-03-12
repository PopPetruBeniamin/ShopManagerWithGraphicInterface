package com.example.lab4_map.Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected final int id;

    public Entity(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public abstract void updateFrom(Entity other);
}
