package com.example.lab4_map.Domain;

public class ProductConverter extends EntityConverter<Product> {
    @Override
    public String toString(Product product) {
        return product.getID() + "," + product.getName() + "," + product.getCategory() + "," + product.getPrice();
    }

    @Override
    public Product fromString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String category = parts[2];
        int price = Integer.parseInt(parts[3]);
        return new Product(id, name, category, price);
    }
}
