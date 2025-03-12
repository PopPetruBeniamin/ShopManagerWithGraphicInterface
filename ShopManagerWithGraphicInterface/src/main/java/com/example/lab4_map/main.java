package com.example.lab4_map;

import com.example.lab4_map.Config.SettingsManager;
import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Domain.OrderConverter;
import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Domain.ProductConverter;
import com.example.lab4_map.Repository.InMemoryRepository;
import com.example.lab4_map.Repository.RepositoryFactory;
import com.example.lab4_map.Repository.SQLOrdersRepository;
import com.example.lab4_map.Repository.SQLProductsRepository;
import com.example.lab4_map.Service.Service;
import com.example.lab4_map.Ui.Ui;

import java.time.LocalDate;
import java.util.List;

public class main {
    public static void main(String[] args) {
        // Charging settings from a settings file
        SettingsManager settings = new SettingsManager("src\\main\\java\\com\\example\\lab4_map\\Config\\settings.properties");
        // Creating the Repository factory

        InMemoryRepository<Product> productsRepository;
        InMemoryRepository<Order> orderRepository;

        // Instantiation the Repository
        if(settings.getProperty("Repository").equalsIgnoreCase("sql")) {
            productsRepository = new SQLProductsRepository();
            orderRepository = new SQLOrdersRepository();
        } else {
            RepositoryFactory factory = new RepositoryFactory(settings);
            productsRepository = factory.createRepository("Products", new ProductConverter());
            orderRepository = factory.createRepository("Orders", new OrderConverter());
        }

        // Creating some product examples if the repository is empty
        Product givenProduct1 = new Product("CocaCola", "Food", 2);
        Product givenProduct2 = new Product("Iphone16", "Technology", 3);
        Product givenProduct3 = new Product("Sofa", "Furniture", 10);
        Product givenProduct4 = new Product("Batman", "Entertainment", 10);
        Product givenProduct5 = new Product("Matilda", "Books", 10);

        if(productsRepository.getAll().isEmpty()){
            productsRepository.add(givenProduct1);
            productsRepository.add(givenProduct2);
            productsRepository.add(givenProduct3);
            productsRepository.add(givenProduct4);
            productsRepository.add(givenProduct5);
        }


        // Creating some order examples if the repository is empty
        List<Integer> products1 = List.of(givenProduct1.getID(), givenProduct1.getID(), givenProduct2.getID());
        List<Integer> products2 = List.of(givenProduct3.getID(), givenProduct4.getID());
        List<Integer> products3 = List.of(givenProduct5.getID(), givenProduct5.getID());

        Order order1 = new Order(LocalDate.of(2024, 12, 12), products1);
        Order order2 = new Order(LocalDate.of(2024, 12, 11), products2);
        Order order3 = new Order(LocalDate.of(2024, 12, 10), products3);

        if(orderRepository.getAll().isEmpty()){
            orderRepository.add(order1);
            orderRepository.add(order2);
            orderRepository.add(order3);
        }

        // Creating service and user interface
        Service orderProductService = new Service(productsRepository, orderRepository);
        Ui ui = new Ui(orderProductService);

        // Start the user interface
        ui.start();
    }
}
