package com.example.lab4_map.Ui;

import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private final Service orderProductService;
    private final Scanner scanner;

    private Product readingAProduct() {
        System.out.print("Type the name of the product:");
        String name = scanner.next();
        System.out.print("Type the category of the product:");
        String category = scanner.next();
        System.out.print("Type the price of the product:");
        int price = scanner.nextInt();

        return new Product(name, category, price);
    }

    public Ui(Service orderProductService) {
        this.orderProductService = orderProductService;
        this.scanner = new Scanner(System.in);
    }

    public void printMenu(){
        System.out.println("=====Product functions=====");
        System.out.println("1. Add product");
        System.out.println("2. Delete product");
        System.out.println("3. Update product");
        System.out.println("4. Print All Products");
        System.out.println("=====Order functions=====");
        System.out.println("5. Add Order");
        System.out.println("6. Delete Order");
        System.out.println("7. Update Order");
        System.out.println("8. Print All Orders");
        System.out.println("0. Exit");
        System.out.print("Type your option:");
    }

    public void start(){
        while(true){
            printMenu();
            int option = scanner.nextInt();

            switch(option){
                case 1 -> addProduct();
                case 2 -> deleteProduct();
                case 3 -> updateProduct();
                case 4 -> printAllProducts();
                case 5 -> addOrder();
                case 6 -> deleteOrder();
                case 7 -> updateOrder();
                case 8 -> printAllOrders();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("!! Invalid option !!\n");
            }
        }
    }

    //Product functions
    void addProduct(){
        Product newProduct = readingAProduct();
        orderProductService.addProduct(newProduct);
    }
    void deleteProduct(){
        System.out.println("Type the ID of the product to delete:");
        int id = scanner.nextInt();
        orderProductService.deleteProductByID(id);
    }
    void updateProduct(){
        System.out.print("Type the ID of the product to update:");
        int id = scanner.nextInt();
        Product newProduct = readingAProduct();
        orderProductService.updateProduct(id, newProduct);
    }
    void printAllProducts(){
        for(Product p : orderProductService.getAllProducts()){
            System.out.println(p.toString());
        }
    }

    //Order functions
    void addOrder(){
        //TODO: Adding only products by ID, that there are in the ProductRepository
        System.out.print("How many products would you like to add?:");
        int quantity = scanner.nextInt();

        List<Integer> products = new ArrayList<>();

        //Reading the products of the order
        while(quantity > 0){
            System.out.print("Type the ID of the product to add:");
            int id = scanner.nextInt();
            Product newProduct = orderProductService.getProductByID(id);
            if(null == newProduct){
                System.out.println("Product not found!");
                continue;
            }
            else
                products.add(newProduct.getID());
            --quantity;
        }

        //The buffer now is free
        scanner.nextLine();

        // Reading the date of the order
        LocalDate orderDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean validDate = false;

        while (!validDate) {
            System.out.print("Enter the order date (dd-MM-yyyy): ");
            String dateString = scanner.nextLine();
            orderDate = LocalDate.parse(dateString, formatter);
            validDate = true;  // Valid date, going out of the while
        }

        Order newOrder = new Order(orderDate, products);

        //Adding the order
        orderProductService.addOrder(newOrder);
        System.out.println("Order created successfully!");
    }

    void deleteOrder(){
        System.out.println("Type the ID of the order to delete:");
        int id = scanner.nextInt();
        orderProductService.deleteOrderByID(id);
    }

    void updateOrder(){
      //todo: implementation
    }

    void printAllOrders(){
        for(Order order : orderProductService.getAllOrders()){
            System.out.println(order.toString());
        }
    }
}
