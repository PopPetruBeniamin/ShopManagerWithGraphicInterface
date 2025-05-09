package com.example.lab4_map.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
    private final Properties properties = new Properties();

    public SettingsManager(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
