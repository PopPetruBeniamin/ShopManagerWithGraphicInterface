package com.example.lab4_map.Repository;

import javafx.collections.ObservableList;

import java.util.List;

public interface GenericRepository<T> {
    void add(T t);
    void updateByID(int ID,T t);
    void deleteByID(int ID);
    T getByID(int ID);
    List<T> getAll();
    ObservableList<T> getObservableList();
}
