package com.example.lab4_map.Repository;

import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Repository.Exceptions.RepositoryExceptions;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLOrdersRepository extends InMemoryRepository<Order> implements AutoCloseable {
    private static final String JDBC_URL =
            "jdbc:sqlite:src/main/java/com/example/orders.db";

    private Connection conn = null;

    public SQLOrdersRepository() {
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM orders");
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    String productsString = rs.getString("products");

                    List<Integer> productIDs = convertStringToList(productsString);

                    // Crea un objeto Order y añádelo a la lista
                    Order order = new Order(id, date, productIDs);
                    items.add(order);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error trying to connect with data base", e);
        }
    }

    private void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders(id INTEGER PRIMARY KEY, date TEXT, products TEXT);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema: " + e.getMessage());
        }
    }

    @Override
    public void add(Order order) throws RepositoryExceptions.RepositoryException {
        super.add(order);

        // Inserta el pedido en la base de datos
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO orders (id, date, products) VALUES (?, ?, ?)")) {
                statement.setInt(1, order.getID());
                statement.setString(2, order.getDate().toString());
                statement.setString(3, convertListToString(order.getIDProducts())); // Convierte la lista de IDs en un string
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Error saving into database");
        }
    }

    @Override
    public void deleteByID(int ID) {
        super.deleteByID(ID);
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM orders WHERE id = ?")) {
                statement.setInt(1, ID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Eroare la salvarea in baza de date");
        }
    }

    @Override
    public void updateByID(int ID, Order order) throws RepositoryExceptions.RepositoryException {
        super.updateByID(ID, order);
        try {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE orders SET date=?, products=? WHERE id=?")) {
                statement.setString(1, order.getDate().toString());
                statement.setString(2, convertListToString(order.getIDProducts())); // Convierte la lista de IDs en un string
                statement.setInt(3, order.getID());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Error updating into database");
        }
    }

    private String convertListToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private List<Integer> convertStringToList(String str) {
        List<Integer> result = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            String[] parts = str.split(",");
            for (String part : parts) {
                result.add(Integer.parseInt(part.trim()));
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
