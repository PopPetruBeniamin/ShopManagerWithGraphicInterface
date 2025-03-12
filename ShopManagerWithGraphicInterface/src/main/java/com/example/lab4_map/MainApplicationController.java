package com.example.lab4_map;

import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Repository.Exceptions.RepositoryExceptions;
import com.example.lab4_map.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainApplicationController {

    //Product
    @FXML
    public TextField TextFieldProductId;
    @FXML
    public TextField TextFieldProductName;
    @FXML
    public TextField TextFieldProductCategory;
    @FXML
    public TextField TextFieldProductPrice;
    @FXML
    public TextField TextFieldDeleteProductId;
    @FXML
    public TextField TextFieldUpdateProductId;
    @FXML
    public TextField TextFieldUpdateProductName;
    @FXML
    public TextField TextFieldUpdateProductCategory;
    @FXML
    public TextField TextFieldUpdateProductPrice;

    //Order
    @FXML
    public TextField TextFieldOrderId;
    @FXML
    public TextField TextFieldOrderDate;
    @FXML
    public TextField TextFieldOrderProducts;
    @FXML
    public TextField TextFieldDeleteOrderId;
    @FXML
    public TextField TextFieldUpdateOrderId;
    @FXML
    public TextField TextFieldUpdateOrderDate;
    @FXML
    public TextField TextFieldUpdateOrderProducts;

    @FXML
    public ListView listViewProducts;
    @FXML
    public ListView listViewOrders;

    private Service service;
    private ObservableList<Product> observableProducts = FXCollections.observableArrayList();
    private ObservableList<Order> observableOrders = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;

        observableProducts.setAll(service.getAllProducts());
        observableOrders.setAll(service.getAllOrders());
        listViewProducts.setItems(observableProducts);
        listViewOrders.setItems(observableOrders);
    }

    private void refreshProductList() {
        // Syncing the observable list with the data
        observableProducts.setAll(service.getAllProducts());
    }

    private void refreshOrderList() {
        // Syncing the observable list with the data
        observableOrders.setAll(service.getAllOrders());
    }

    @FXML
    private void handleAddProduct() {
        try {
            int id = Integer.parseInt(TextFieldProductId.getText());
            String name = TextFieldProductName.getText();
            String category = TextFieldProductCategory.getText();
            int price = Integer.parseInt(TextFieldProductPrice.getText());

            Product product;
            if(TextFieldProductId.getText().isEmpty()){
                product = new Product(name, category, price);
            } else {
                product = new Product(id, name, category, price);
            }
            service.addProduct(product);

            refreshProductList();

            // Clean fields
            TextFieldProductId.clear();
            TextFieldProductName.clear();
            TextFieldProductCategory.clear();
            TextFieldProductPrice.clear();
        } catch (RepositoryExceptions.EntityNotAddedException e) {
            showAlert("Error", "Duplicate ID", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleDeleteProduct() {
        try {
            int id = Integer.parseInt(TextFieldDeleteProductId.getText());
            service.deleteProductByID(id);

            refreshProductList();

            TextFieldDeleteProductId.clear();
        } catch (RepositoryExceptions.EntityNotFoundException e) {
            showAlert("Error", "Product not founded", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleUpdateProduct() {
        try {
            int id = Integer.parseInt(TextFieldUpdateProductId.getText());
            String name = TextFieldUpdateProductName.getText();
            String category = TextFieldUpdateProductCategory.getText();
            int price = Integer.parseInt(TextFieldUpdateProductPrice.getText());

            Product product = new Product(id, name, category, price);
            service.updateProduct(id, product);

            refreshProductList();

            TextFieldUpdateProductId.clear();
            TextFieldUpdateProductName.clear();
            TextFieldUpdateProductCategory.clear();
            TextFieldUpdateProductPrice.clear();
        } catch (RepositoryExceptions.EntityNotFoundException e) {
            showAlert("Error", "Product not founded", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddOrder() {
        try {
            int id = Integer.parseInt(TextFieldOrderId.getText());
            LocalDate date = LocalDate.parse(TextFieldOrderDate.getText());
            String products = TextFieldOrderProducts.getText();
            String[] parts = products.split(",");
            List<Integer> partsInts = new ArrayList<>();
            for(String part : parts) {
                partsInts.add(Integer.parseInt(part));
            }
            Order order = new Order(id, date, partsInts);
            service.addOrder(order);

            refreshOrderList();

            TextFieldOrderId.clear();
            TextFieldOrderDate.clear();
            TextFieldOrderProducts.clear();
        } catch (RepositoryExceptions.EntityNotAddedException e) {
            showAlert("Error", "Duplicate ID", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleDeleteOrder() {
        try {
            int id = Integer.parseInt(TextFieldDeleteOrderId.getText());
            service.deleteOrderByID(id);

            refreshOrderList();

            TextFieldDeleteOrderId.clear();
        } catch (RepositoryExceptions.EntityNotFoundException e) {
            showAlert("Error", "Order not founded", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleUpdateOrder() {
        try {
            int id = Integer.parseInt(TextFieldUpdateOrderId.getText());
            LocalDate date = LocalDate.parse(TextFieldUpdateOrderDate.getText());
            String products = TextFieldUpdateOrderProducts.getText(); // Procesa esto según tu lógica
            String[] parts = products.split(",");
            List<Integer> partsInts = new ArrayList<>();
            for(String part : parts) {
                partsInts.add(Integer.parseInt(part));
            }
            Order order = new Order(id, date, partsInts); // Ajusta según el constructor
            service.updateOrder(id, order);

            refreshOrderList();

            TextFieldUpdateOrderId.clear();
            TextFieldUpdateOrderDate.clear();
            TextFieldUpdateOrderProducts.clear();
        } catch (RepositoryExceptions.EntityNotFoundException e) {
            showAlert("Error", "Order not founded", e.getMessage(), Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid input", "Please enter a valid number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Invalid input", "Please check your input and try again.", Alert.AlertType.ERROR);
        }
    }

    //For the 1st statistic
    public void showCategoryStats() {
        Map<String, Map<String, Integer>> stats = service.countCategoriesWithOrderCount();
        StringBuilder content = new StringBuilder();
        stats.forEach((category, counts) -> {
            content.append("Category: ").append(category).append("\n");
            content.append("Total Products: ").append(counts.getOrDefault("totalProducts", 0)).append("\n");
            content.append("Total Orders: ").append(counts.getOrDefault("totalOrders", 0)).append("\n\n"); });
        showAlert("Category Stats", "Results:", content.toString(), Alert.AlertType.INFORMATION); }

    //For the 2nd statistic
    public void showMostProfitableMonths() {
        Map<Month, Map<String, Double>> stats = service.getProfitByMonth();
        StringBuilder content = new StringBuilder();
        stats.forEach((month, counts) -> {
            content.append("Month: ").append(month).append("\n");
            content.append("Total Orders: ").append(counts.get("totalOrders").intValue()).append("\n");
            content.append("Total Price: ").append(counts.get("totalPrice")).append("\n\n"); });
        showAlert("Most profitable months", "Results:", content.toString(), Alert.AlertType.INFORMATION);
    }

    //For the 3rd statistic
    public void showMostProfitableProducts() {
        List<Map.Entry<Product, Double>> profitableProducts = service.getTotalRevenueByProduct();
        StringBuilder content = new StringBuilder();
        profitableProducts.forEach(entry -> {
            Product product = entry.getKey();
            double revenue = entry.getValue();
            content.append("Product: ").append(product.getName()).append("\n");
            content.append("Category: ").append(product.getCategory()).append("\n");
            content.append("Price: ").append(product.getPrice()).append("\n");
            content.append("Total Incomes: ").append(revenue).append("\n\n");
        });
        showAlert("Most Profitable Products", "Results:", content.toString(), Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);

        TextArea textArea = new TextArea(content);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(480, 320);

        alert.showAndWait();
    }
}