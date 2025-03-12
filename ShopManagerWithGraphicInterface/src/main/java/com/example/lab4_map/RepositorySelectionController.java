//package com.example.lab4_map;
//
//import com.example.lab4_map.Domain.OrderConverter;
//import com.example.lab4_map.Domain.ProductConverter;
//import com.example.lab4_map.Repository.*;
//import com.example.lab4_map.Service.Service;
//import javafx.fxml.FXML;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Alert;
//
//public class RepositorySelectionController {
//    private MainApplication mainApp;
//
//    @FXML
//    private ComboBox<String> repositoryTypeComboBox;
//
//    public void setMainApp(MainApplication mainApp) {
//        this.mainApp = mainApp;
//    }
//
//    @FXML
//    private void handleSelectRepository() {
//        String selectedRepo = repositoryTypeComboBox.getValue();
//
//        if (selectedRepo == null) {
//            // Mostrar una alerta si no se selecciona ningún repositorio
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Attention");
//            alert.setHeaderText("It was no repository selected");
//            alert.setContentText("Please, select a repository.");
//            alert.showAndWait();
//            return;
//        }
//
//        Service service;
//        switch (selectedRepo) {
//            case "InMemoryRepository":
//                service = new Service(new InMemoryRepository<>(), new InMemoryRepository<>());
//                break;
//            case "TextFileRepository":
//                service = new Service(new TextFileRepository<>("src\\main\\java\\com\\example\\lab4_map\\Data\\TextFiles\\products.txt", new ProductConverter()),
//                                      new TextFileRepository<>("src\\main\\java\\com\\example\\lab4_map\\Data\\TextFiles\\orders.txt", new OrderConverter()));
//                break;
//            case "BinaryFileRepository":
//                service = new Service(new BinaryFileRepository<>("src\\main\\java\\com\\example\\lab4_map\\Data\\BinFiles\\products.bin"),
//                                      new BinaryFileRepository<>("src\\main\\java\\com\\example\\lab4_map\\Data\\BinFiles\\orders.bin"));
//                break;
//            case "SQLRepository":
//                service = new Service(new SQLProductsRepository(), new SQLOrdersRepository());
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown repository type: " + selectedRepo);
//        }
//
//        try {
//            mainApp.showMainUI(service);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
