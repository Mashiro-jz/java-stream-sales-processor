# E-commerce Sales Analyzer (Spring Boot REST API)

## About the Project
**E-commerce Sales Analyzer** is a robust analytical REST API built with Spring Boot that simulates the backend reporting system for an online store. Originally a console-based logic processor, it has evolved into a fully containerized web application with a persistence layer and interactive API documentation.

The project demonstrates a modern layered architecture, processing and aggregating large datasets of sales records using a declarative and functional programming approach (Java Streams API) directly over data retrieved from an embedded database.

## Key Features
- **RESTful API** – Exposes analytical endpoints to integrate with frontend dashboards or external reporting tools.
- **In-Memory Persistence** – Automatically seeds an H2 database from a `.csv` file upon application startup using a custom `DatabaseInitializer`.
- **Interactive Documentation** – Fully documented API endpoints using Swagger/OpenAPI 3.
- **Dockerized** – Packaged as a lightweight, portable Docker container for instant deployment.
- **Business Logic Highlights:**
    - **Total Revenue Calculation:** Sums the value of all successfully completed orders.
    - **Key Transaction Identification:** Extracts the top 3 most expensive completed orders.
    - **User Activity Analysis:** Retrieves a distinct list of unique customers who placed an order on a specific date.
    - **Data Grouping:** Categorizes all orders based on their respective product departments.
    - **Financial Reporting by Category:** Calculates total revenue generated within each specific product category.

## Technology Stack
- **Language:** Java 24
- **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA)
- **Database:** H2 (In-Memory)
- **Documentation:** Swagger / OpenAPI (`springdoc-openapi`)
- **Containerization:** Docker
- **Testing:** JUnit 5, Mockito, MockMvc (Integration Testing), AssertJ
- **Build Tool:** Maven

## API Endpoints
Once the application is running, the following endpoints are available under the `/api/sales` path:
- `GET /revenue` - Returns total revenue for completed orders.
- `GET /top-orders` - Returns the top 3 most expensive completed orders.
- `GET /categories` - Returns a map grouping all orders by product category.
- `GET /categories/revenue` - Returns total revenue grouped by category.
- `GET /customers?date=YYYY-MM-DD` - Returns unique customers for a given date.

## How to Run the Application
Just ensure Docker Desktop is running.
1. Build the image:
   ```bash
   docker build -t sales-analyzer .
   ```
2. Run the container:
   ```
   docker run -p 8080:8080 sales-analyzer
   ```

## Swagger UI Documentation
Once the server is up and running (either via Docker or Maven), you can explore and test the endpoints interactively by visiting:http://localhost:8080/swagger-ui.html