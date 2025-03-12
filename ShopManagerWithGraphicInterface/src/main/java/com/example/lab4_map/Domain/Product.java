package com.example.lab4_map.Domain;

public class Product extends Entity {
    private String name;
    private String category;
    private double price;

    public Product() {
        super(-1);
        this.name = "";
        this.category = "";
        this.price = 0;
    }

    public Product(String name, String category, double price) {
        super(IDGenerator.generatorProductID());
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, String category, double price) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
    }

    //Get functions
    public int getID() {
        return super.getId();
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }

    //Set functions
    public void setCategory(String category) {
        this.category = category;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void updateFrom(Entity other) {
        if(other instanceof Product product) {
            this.name = product.getName();
            this.category = product.getCategory();
            this.price = product.getPrice();
        }
        else {
            throw new IllegalArgumentException("Not a Product");
        }
    }

    @Override
    public String toString() {
        return "Product," + id + "," + name + "," + category + "," + price;
    }
}
