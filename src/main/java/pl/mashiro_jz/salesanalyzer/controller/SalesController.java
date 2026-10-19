package pl.mashiro_jz.salesanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mashiro_jz.salesanalyzer.model.Order;
import pl.mashiro_jz.salesanalyzer.service.SalesAnalyzer;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesAnalyzer analyzer;

    public SalesController(SalesAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    // Dostępne pod: http://localhost:8080/api/sales/revenue
    @GetMapping("/revenue")
    public double getRevenue() {
        return analyzer.calculateTotalRevenue();
    }

    // Dostępne pod: http://localhost:8080/api/sales/top-orders
    @GetMapping("/top-orders")
    public List<Order> getTopOrders() {
        return analyzer.getTop3MostExpensiveOrders();
    }

    // Dostępne pod: http://localhost:8080/api/sales/categories
    @GetMapping("/categories")
    public Map<String, List<Order>> getOrdersByCategory() {
        return analyzer.groupOrdersByCategory();
    }

    // Dostępne pod: http://localhost:8080/api/sales/categories/revenue
    @GetMapping("/categories/revenue")
    public Map<String, Double> getRevenueByCategory(){
        return analyzer.calculateRevenuePerCategory();
    }

    @GetMapping("/customers")
    public List<String> getCustomers(@RequestParam("date") LocalDate date) {
        return analyzer.getUniqueCustomersByDate(date);
    }
}