package com.example.lab4_map;

import com.example.lab4_map.Config.SettingsManager;
import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Domain.OrderConverter;
import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Domain.ProductConverter;
import com.example.lab4_map.Repository.*;
import com.example.lab4_map.Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainApplication extends Application {
    //private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //this.primaryStage = primaryStage;  --- This line will be useful when I would do more windows
        
        SettingsManager settings = new SettingsManager("src\\main\\java\\com\\example\\lab4_map\\Config\\settings.properties");
        // Creating the Repository factory

        InMemoryRepository<Product> repositoryProducts;
        InMemoryRepository<Order> repositoryOrders;

        // Instantiation the Repository
        if(settings.getProperty("Repository").equalsIgnoreCase("SQL")) {
            repositoryProducts = new SQLProductsRepository();
            repositoryOrders = new SQLOrdersRepository();
        } else {
            RepositoryFactory factory = new RepositoryFactory(settings);
            repositoryProducts = factory.createRepository("Products", new ProductConverter());
            repositoryOrders = factory.createRepository("Orders", new OrderConverter());
        }

        //generateTestData(repositoryProducts, repositoryOrders);
        Service service = new Service(repositoryProducts, repositoryOrders);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-application-view.fxml"));
        Parent root = loader.load();

        MainApplicationController controller = loader.getController();
        controller.setService(service);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("My shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateTestData(InMemoryRepository<Product> productRepo, InMemoryRepository<Order> orderRepo) {
        Random random = new Random();

        for (int i = 1; i <= 5; i++) {
            Product product = new Product(i, "Product" + i, "Category" + (i % 10), random.nextInt(10, 700));
            productRepo.add(product);
        }

        for (int i = 1; i <= 5; i++) {
            List<Integer> productsInOrder = new ArrayList<>();
            int productCount = random.nextInt(1, 6); // He order would have between 1 and 5 products

            for (int j = 0; j < productCount; j++) {
                int productId = random.nextInt(1, 201); // IDs between 1 and 200
                productsInOrder.add(productId);
            }

            Order order = new Order(i, LocalDate.now().plusDays(random.nextInt(-180, 180)), productsInOrder);
            orderRepo.add(order);
        }
    }

    //
    public static void main(String[] args) {
        launch();
    }
}




//        private void showRepositorySelection() throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("repository-selection-view.fxml"));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//
//        RepositorySelectionController selectionController = loader.getController();
//        selectionController.setMainApp(this);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Select the type of the repository");
//        primaryStage.show();
//    }
//
//        public void showMainUI(Service service) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-application-view.fxml"));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//
//
//        MainApplicationController mainController = loader.getController();
//        mainController.setService(service);
//
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("My Shop");
//        primaryStage.show();
//    }

