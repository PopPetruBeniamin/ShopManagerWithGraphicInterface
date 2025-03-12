package com.example.lab4_map.Domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OrderConverter extends EntityConverter<Order> {
    @Override
    public String toString(Order order) {
        String products = String.join("|", order.getIDProducts().stream().map(String::valueOf).toArray(String[]::new));
        return order.getID() + "," + order.getDate() + "," + products;
    }

    @Override
    public Order fromString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        LocalDate date = LocalDate.parse(parts[1]);
        // Convierte la cadena de productos separada por '|' a una lista de enteros
        List<Integer> products = Arrays.stream(parts[2].split("\\|"))
                .map(Integer::parseInt)
                .toList();
        return new Order(id, date, products);
    }
}
