package com.example.lab4_map.Repository;//package demo.Repository;
//
//import demo.Domain.Entity;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.type.TypeReference;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class JSONRepository<T extends Entity<T>> extends FileRepository<T> {
//
//    private final ObjectMapper objectMapper;
//
//    public JSONRepository(String fileName) {
//        super(fileName);
//        this.objectMapper = new ObjectMapper();
//    }
//
//    @Override
//    protected void writeToFile() {
//        try {
//            objectMapper.writeValue(file, new ArrayList<>(items));
//        } catch (IOException e) {
//            throw new RuntimeException("Error writing to JSON file", e);
//        }
//    }
//
//    @Override
//    protected void loadFromFile() {
//        if (!file.exists()) return;
//
//        try {
//            items.clear();
//            items.addAll(objectMapper.readValue(file, new TypeReference<List<T>>() {}));
//        } catch (IOException e) {
//            throw new RuntimeException("Error loading from JSON file", e);
//        }
//    }
//}
//
