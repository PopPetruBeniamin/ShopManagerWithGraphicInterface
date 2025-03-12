package com.example.lab4_map.Repository;

import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Repository.Exceptions.RepositoryExceptions;
import org.sqlite.SQLiteDataSource;

import java.sql.*;


public class SQLProductsRepository extends InMemoryRepository<Product> implements AutoCloseable {
    private static final String JDBC_URL =
            "jdbc:sqlite:src/main/java/com/example/products.db";

    private Connection conn = null;

    public SQLProductsRepository() {
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from products");
                 ResultSet rs = statement.executeQuery();) {
                 while (rs.next()) {
                    Product product = new Product(rs.getInt("id"), rs.getString("name"),
                            rs.getString("category"), rs.getDouble("price"));
                    items.add(product);
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
            throw new RuntimeException("Eroare la conectarea cu baza de date", e);
        }
    }

    private void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS products(id int PRIMARY KEY, name varchar(100), category varchar(100), price double);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    @Override
    public void add(Product elem) throws RepositoryExceptions.RepositoryException {
        super.add(elem);
        // daca se ajunge aici, trebuie actualizata baza de date

        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO products (id, name, category, price) VALUES (?, ?, ?, ?)")) {
                statement.setInt(1, elem.getId());
                statement.setString(2, elem.getName());
                statement.setString(3, elem.getCategory());
                statement.setDouble(4, elem.getPrice());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Error adding into database");
        }
    }

    @Override
    public void deleteByID(int ID) {
        super.deleteByID(ID);

        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM products WHERE id = ?")) {
                statement.setInt(1, ID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Error deleting into database");
        }
    }

    @Override
    public void updateByID(int ID, Product product) throws RepositoryExceptions.RepositoryException {
        super.updateByID(ID, product);
        try {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE products SET name=?, category=?, price=? WHERE id=?")) {
                statement.setString(1, product.getName());
                statement.setString(2, product.getCategory());
                statement.setDouble(3, product.getPrice());
                statement.setInt(4, product.getID());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositoryExceptions.RepositoryException("Error updating into database");
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            // In metoda close e descurajata aruncarea de exceptii
            e.printStackTrace();
        }
    }
}
