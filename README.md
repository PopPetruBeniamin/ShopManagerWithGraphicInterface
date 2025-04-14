# 📌 Inventory & Order Management System

## 📖 Description
This application is designed for **Inventory and Order Management** using **Java** and **JavaFX** for the graphical interface. It allows users to manage products and orders, with functionalities to save data in files (text or binary) or an SQLite database. The system also provides various statistics, such as popular categories, monthly revenue, and top-selling products.

---

## ✨ Key Features

### 🔹 **1. Data Persistence**
- **Text Files**: Store data in a human-readable format.
- **Binary Files**: Efficient storage of data in binary format.
- **SQLite Database**: Database support for storing products and orders.

### 🔹 **2. Product & Order Management**
- 📌 **Add/Remove/Update Products**: Manage product inventory efficiently.
- 📌 **Add/Remove/Update Orders**: Process orders and track their status.
- 📌 **View Products/Orders**: Display a list of all stored products and orders.
  
### 🔹 **3. Statistical Analysis**
- 📊 **Popular Categories**: Display the most popular product categories based on orders.
- 📊 **Monthly Revenue**: Generate reports for monthly earnings.
- 📊 **Top-Selling Products**: Track products with the highest revenue.

### 🔹 **4. User Interface Options**
- **Graphical Interface (JavaFX)**: Easy-to-use visual interface for interaction.
- **Command-Line Interface (CLI)**: Simple text-based interface for quick actions.

---

## 🏗️ Code Structure
The program is structured into:
- 📂 **MainMain.java**: Entry point: launches either GUI or CLI based on configuration.
- 📂 **MainApplication.java**: Launches the graphical user interface (JavaFX).
- 📂 **main.java**: Starts the command-line interface.
- 📂 **Config/**: settings.properties # Configuration file to specify startup options (JavaFX or CLI) | SettingsManager.java # Class to manage reading settings
- 📂 **Data/**: Package for binary and text file persistence.
- 📂 **Domain/**: Business models: Product, Order.
- 📂 **Repository/**: Storage logic (in-memory, DB, file-based).
- 📂 **Service/**: Business logic and statistics processing.
- 📂 **Ui/**: Command-line interface

---
## 🏆 Conclusion

This **Inventory & Order Management System** is a practical project developed to explore concepts such as file handling, database integration, and JavaFX interfaces. While it's a relatively simple tool, it provides a solid foundation for managing basic product and order data, and it includes some useful statistics to help better understand inventory trends. It's a great starting point for learning and expanding into more advanced systems. 🚀
