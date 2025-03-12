package com.example.lab4_map.Repository;

import com.example.lab4_map.Domain.Entity;

public abstract class AbstractFileRepository<T extends Entity> extends InMemoryRepository<T> {
    protected String file;

    public AbstractFileRepository(String fileName) {
        this.file = fileName;
    }

    // Métodos abstractos para que las subclases definan el formato específico
    @Override
    public void add(T entity) {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void deleteByID(int id) {
        super.deleteByID(id);
        writeToFile();
    }

    @Override
    public void updateByID(int ID, T updatedItem) {
        super.updateByID(ID, updatedItem);
        writeToFile();
    }

    protected abstract void writeToFile();

    protected abstract void loadFromFile();
}

