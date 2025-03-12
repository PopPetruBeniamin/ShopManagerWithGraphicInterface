package com.example.lab4_map.Domain;

public abstract class EntityConverter<T> {
    public abstract String toString(T shape);
    public abstract T fromString(String line);
}
