# E-commerce Sales Analyzer

## About the Project
**E-commerce Sales Analyzer** is an analytical module developed in Java that simulates the business logic of a reporting system for an online store. The project is designed to process, filter, and aggregate large datasets of sales records.

The primary goal of this repository is to demonstrate advanced proficiency in the **Java Streams API**, **Lambda Expressions**, and **Collections**. It solves real-world business problems using a declarative and functional programming approach, entirely eliminating the need for traditional `for` or `while` loops.

## Features
The application processes a list of `Order` objects to generate the following analytical insights:
* **Total Revenue Calculation** – Sums the value of all successfully completed orders.
* **Key Transaction Identification** – Extracts the top 3 most expensive completed orders in the dataset.
* **User Activity Analysis** – Generates a distinct list of unique customers who placed an order on a specific business day.
* **Data Grouping** – Categorizes all orders based on their respective product departments (e.g., Electronics, Books).
* **Financial Reporting by Category** – Calculates the total revenue generated within each specific product category.

## Technology Stack
* **Language:** Java (17 / 21)
* **Paradigms:** Object-Oriented Programming (OOP), Functional Programming
* **Core APIs:** Java Streams API, `java.util.function`, `java.util.stream.Collectors`
* **Data Structures:** `List`, `Map`, `Set`
* **Build Tool:** Maven
* **Architecture:** Layered design separating the data model from the business logic.
