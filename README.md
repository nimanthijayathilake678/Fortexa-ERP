# Fortexa - ERP System

The **Fortexa** project is a modern, microservices-based e-commerce system designed to manage catalogues, orders, inventories, users, sellers, customers, and locations for retail businesses such as clothing shops. Built using **Java Spring Boot**, the system leverages **MongoDB** and **MySQL** for data storage and follows best practices for scalability, maintainability, and performance.

---

## Features

### Core Services
1. **Catalogue Service**  
   - Manage products, categories, and materials (e.g., T-shirts, sizes, colors).  
   - Flexible schema for dynamic product attributes.  
2. **Order Service**  
   - Handle customer and internal orders.  
   - Support for order tracking, payments, and fulfillment.  
3. **Inventory Service**  
   - Real-time stock tracking with barcode/item-level granularity.  
   - Batch management for tracking bulk purchases from sellers.  
4. **User Service**  
   - Role-based access control (e.g., owner, accountant, cashier).  
   - Authentication and authorization using JWT.  
5. **Seller Service**  
   - Manage seller information and bulk purchase agreements.  
6. **Customer Service**  
   - Manage customer profiles, loyalty programs, and order history.  
7. **Location Service**  
   - Track warehouses, shops, and storage zones.  
8. **Notification Service**  
   - Send email/SMS alerts for orders, low stock, and system events.  

---

## Technologies Used
- **Spring Boot**: Backend framework for building microservices.  
- **MongoDB**: NoSQL database for flexible data storage (Catalogue, Inventory, Notification services).  
- **MySQL**: Relational database for structured data (Order, User, Seller, Customer, Location services).  
- **Kafka/RabbitMQ**: Message broker for event-driven communication.  
- **Swagger**: API documentation.  
- **JWT**: Security and authentication.  
- **Docker & Kubernetes**: Containerization and orchestration.  

---

## Prerequisites
- **Java 17+**  
- **Maven 3.9+** or **Gradle 8.4+**  
- **MongoDB 6.0+** (local or Atlas)  
- **MySQL 8.0+**  
- **Docker** (optional for containerization)  

---

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/retailhub.git
cd retailhub
