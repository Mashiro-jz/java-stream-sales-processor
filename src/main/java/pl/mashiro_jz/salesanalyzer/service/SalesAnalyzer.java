package pl.mashiro_jz.salesanalyzer.service;

import pl.mashiro_jz.salesanalyzer.model.Order;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesAnalyzer {
    List<Order> orderList;

    public SalesAnalyzer(List<Order> orderList) {
        this.orderList = orderList;
    }

    public double calculateTotalRevenue(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .mapToDouble(Order::price)
                .sum();
    }
    public List<Order> getTop3MostExpensiveOrders(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .sorted(Comparator.comparing(Order::price).reversed())
                .limit(3)
                .toList();
    }
    public List<String> getUniqueCustomersByDate(LocalDate date){
        return orderList.stream()
                .filter(order -> order.date().equals(date))
                .map(Order::customerName)
                .distinct() // usuwa duplikaty
                .toList();
    }
    public Map<String, List<Order>> groupOrdersByCategory(){
        return orderList.stream()
                .collect(Collectors.groupingBy(
                   Order::category
                ));
    }
    public Map<String, Double> calculateRevenuePerCategory(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .collect(Collectors.groupingBy(
                   Order::category,
                        Collectors.summingDouble(Order::price)
                ));
    }
}
