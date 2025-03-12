package com.example.lab4_map.Repository;

import com.example.lab4_map.Domain.Entity;
import com.example.lab4_map.Domain.EntityConverter;

import java.io.*;

public class TextFileRepository<T extends Entity> extends AbstractFileRepository<T> {

    protected EntityConverter<T> converter;

    public TextFileRepository(String fileName, EntityConverter<T> converter) {
        super(fileName);
        this.converter = converter;
        try {
            loadFromFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (var item : items) {
                String asString = converter.toString(item);
                writer.write(asString);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to text file", e);
        }
    }

    @Override
    protected void loadFromFile() {
        File fileObj = new File(file);
        if (!fileObj.exists() || fileObj.length() == 0) {
            items.clear();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                items.add(converter.fromString(line)); // Usa una entidad genérica como referencia
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("The file not found", e);
        } catch (IOException e) {
            throw new RuntimeException("Error loading from text file", e);
        }
    }
}