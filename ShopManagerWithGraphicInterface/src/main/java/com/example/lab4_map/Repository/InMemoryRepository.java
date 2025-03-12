package com.example.lab4_map.Repository;


import com.example.lab4_map.Domain.Entity;
import com.example.lab4_map.Repository.Exceptions.RepositoryExceptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class InMemoryRepository<T extends Entity> implements GenericRepository<T> {
    protected final List<T> items = new ArrayList<>();

    @Override
    public void add(T item) {
        if(getByID(item.getId()) == null) {
            items.add(item);
        }
        else {
            throw new RepositoryExceptions.EntityNotAddedException("Duplicate Id");
        }
    }

    @Override
    public void deleteByID(int ID) {
        boolean removed = items.removeIf(entity -> entity.getId() == ID);
        if(!removed) {
            throw new RepositoryExceptions.EntityNotFoundException("The item was not found!!");
        }
    }

    @Override
    public void updateByID(int ID, T updatedItem) {
        T searchedItem = getByID(ID);
        if(searchedItem != null) {
            searchedItem.updateFrom(updatedItem);
        } else {
            throw new RepositoryExceptions.EntityNotFoundException("The item was not found!!");
        }  // Product not found
    }

    @Override
    public T getByID(int ID) {
        for(T item : items) {
            if(item.getId() == ID) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    @Override
    public ObservableList<T> getObservableList() {
        return FXCollections.observableList(items);
    }
}
