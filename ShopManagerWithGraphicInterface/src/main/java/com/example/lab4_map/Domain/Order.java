package com.example.lab4_map.Domain;

import java.time.LocalDate;
import java.util.List;

public class Order extends Entity {
    private LocalDate date;
    private List<Integer> IDProducts;

    public Order() {
        super(-1);
        this.date = null;
        this.IDProducts = null;
    }

    //Get functions
    public Order(LocalDate date, List<Integer> products) {
        super(IDGenerator.generatorOrderID());
        this.date = date;
        this.IDProducts = products;
    }

    public Order(int id, LocalDate date, List<Integer> products) {
        super(id);
        this.date = date;
        this.IDProducts = products;
    }

    //Get functions
    public int getID() {
        return super.getId();
    }
    public List<Integer> getIDProducts() {
        return IDProducts;
    }
    public LocalDate getDate() {
        return date;
    }

    //Set functions
    public void setIDProducts(List<Integer> IDProducts) {
        this.IDProducts = IDProducts;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void updateFrom(Entity other) {
        if(other instanceof Order order) {
            this.date = order.getDate();
            this.IDProducts = order.getIDProducts();
        }
        else {
            throw new IllegalArgumentException("Not an Order");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Order," + id + ",");
        s.append(date).append(",").append(IDProducts.getFirst());
        for (int i = 1 ; i < IDProducts.size() ; i++) {
            s.append("|").append(IDProducts.get(i));
        }
        return s.toString();
    }
}
