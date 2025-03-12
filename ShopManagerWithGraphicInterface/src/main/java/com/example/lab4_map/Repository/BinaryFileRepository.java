package com.example.lab4_map.Repository;


import com.example.lab4_map.Domain.Entity;
import com.example.lab4_map.Repository.Exceptions.RepositoryExceptions;

import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entity> extends AbstractFileRepository<T> {

    public BinaryFileRepository(String fileName) {
        super(fileName);
        try {
            loadFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(items);
            // TODO If there is an exception raised here, who is responsible for closing the stream?

        } catch (IOException exc) {
            throw new RepositoryExceptions.RepositoryException("Error saving into the file");
        } finally {
            try {
                assert oos != null;
                oos.close();
            } catch (IOException e) {
                throw new RepositoryExceptions.RepositoryException(e.getMessage());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void loadFromFile() {
        File fileObj = new File(file);
        if (!fileObj.exists() || fileObj.length() == 0) {
            items.clear();
            return;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileObj));
            items.clear();
            items.addAll((List<T>) ois.readObject());
        } catch (FileNotFoundException exc){
            throw new RuntimeException("The file not found", exc);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error loading from binary file", e);
        }
    }
}

