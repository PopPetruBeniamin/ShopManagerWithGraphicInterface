package com.example.lab4_map.Repository;

import com.example.lab4_map.Config.SettingsManager;
import com.example.lab4_map.Domain.Entity;
import com.example.lab4_map.Domain.EntityConverter;

public class RepositoryFactory {
    private final SettingsManager settings;

    public RepositoryFactory(SettingsManager settings) {
        this.settings = settings;
    }

    public <T extends Entity> InMemoryRepository<T> createRepository(String key, EntityConverter<T> converter) {
        String repositoryType = settings.getProperty("Repository");
        String fileName = settings.getProperty(key); // e.g., "Products" or "Orders"

        if("inmemory".equalsIgnoreCase(repositoryType)){
            return new InMemoryRepository<>();
        } else if ("binary".equalsIgnoreCase(repositoryType)) {
            return new BinaryFileRepository<>(fileName); // Binary repository
        } else if ("text".equalsIgnoreCase(repositoryType)) {
            return new TextFileRepository<>(fileName, converter); // Text repository (optional)
        } else {
            throw new UnsupportedOperationException("Repository type not supported: " + repositoryType);
        }
    }
}
