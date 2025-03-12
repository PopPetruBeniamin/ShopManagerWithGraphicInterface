package com.example.lab4_map.Service;

import com.example.lab4_map.Domain.Order;
import com.example.lab4_map.Domain.Product;
import com.example.lab4_map.Repository.InMemoryRepository;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private final InMemoryRepository<Order> ordersRepository;
    private final InMemoryRepository<Product> productsRepository;

    public Service(InMemoryRepository<Product> givenProductsRepository, InMemoryRepository<Order> givenOrdersRepository) {
        this.ordersRepository = givenOrdersRepository;
        this.productsRepository = givenProductsRepository;
    }

    //=======================Product functions===========================
    public void addProduct(Product product){
        productsRepository.add(product);
    }
    public void updateProduct(int ID, Product product){
        productsRepository.updateByID(ID, product);
    }
    public void deleteProductByID(int ID){
        productsRepository.deleteByID(ID);
    }
    public Product getProductByID(int ID){
        return productsRepository.getByID(ID);
    }
    public List<Product> getAllProducts(){
        return productsRepository.getAll();
    }

    //For the 1st statistic
    //Numărul de produse comandate din fiecare categorie. Se vor afișa numele fiecărei categorii precum și numărul de produse comandate din acea categorie, în ordine descrescătoare a numărului de comenzi.
    public Map<String, Map<String, Integer>> countCategoriesWithOrderCount() {
        Map<String, Map<String, Integer>> categoryStats = new HashMap<>();

        List<Order> orders = getAllOrders();

        orders.forEach(order -> {
            Set<String> categoriesInOrder = order.getIDProducts().stream()
                    .map(this::getProductByID)
                    .filter(Objects::nonNull)
                    .map(Product::getCategory)
                    .collect(Collectors.toSet());

            categoriesInOrder.forEach(category -> {
                categoryStats.putIfAbsent(category, new HashMap<>());
                categoryStats.get(category).merge("totalOrders", 1, Integer::sum);
            });

            order.getIDProducts().stream()
                    .map(this::getProductByID)
                    .filter(product -> product != null)
                    .map(Product::getCategory)
                    .forEach(category -> {
                        categoryStats.putIfAbsent(category, new HashMap<>());
                        categoryStats.get(category).merge("totalProducts", 1, Integer::sum);
                    });
        });

        return categoryStats.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int totalPedidos1 = e1.getValue().getOrDefault("totalOrders", 0);
                    int totalPedidos2 = e2.getValue().getOrDefault("totalOrders", 0);
                    return Integer.compare(totalPedidos2, totalPedidos1);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    //For the 2nd statistic
    public Map<Month, Map<String, Double>> getProfitByMonth() {
        List<Order> orders = getAllOrders();
        Map<Month, Map<String, Double>> monthlyStats = new HashMap<>();

        for (Order order : orders) {
            Month month = order.getDate().getMonth();
            double totalPrice = order.getIDProducts().stream().
                    map(this::getProductByID).
                    filter(Objects::nonNull).
                    mapToDouble(Product::getPrice).
                    sum();
            monthlyStats.putIfAbsent(month, new HashMap<>());
            monthlyStats.get(month).merge("totalPrice", totalPrice, Double::sum);
            monthlyStats.get(month).merge("totalOrders", 1.0, Double::sum); }
        return monthlyStats.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue().get("totalPrice"), e1.getValue().get("totalPrice")))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new ));
    }

    //For the 3rd statistic
    public List<Map.Entry<Product, Double>> getTotalRevenueByProduct() {
        Map<Integer, Double> revenueMap = new HashMap<>();
        List<Order> orders = getAllOrders();
        orders.forEach(order -> { order.getIDProducts().forEach(productId -> {
            Product product = getProductByID(productId);
            if (product != null) {
                revenueMap.merge(productId, product.getPrice(), Double::sum); } }); });
        return revenueMap.entrySet().stream()
                .map(entry -> Map.entry(getProductByID(entry.getKey()), entry.getValue()))
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());
    }

    //=======================Order functions===========================
    public void addOrder(Order order){
        ordersRepository.add(order);
    }
    public void updateOrder(int ID, Order order){
        ordersRepository.updateByID(ID, order);
    }
    public void deleteOrderByID(int ID){
        ordersRepository.deleteByID(ID);
    }

    public List<Order> getAllOrders(){
        return ordersRepository.getAll();
    }

}
