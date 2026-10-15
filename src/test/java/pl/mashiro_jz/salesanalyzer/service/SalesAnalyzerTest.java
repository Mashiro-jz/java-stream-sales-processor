package pl.mashiro_jz.salesanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mashiro_jz.salesanalyzer.model.Order;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesAnalyzerTest {
    private SalesAnalyzer salesAnalyzer;

    @BeforeEach
    void setUp(){
        LocalDate today = LocalDate.now();

        List<Order> testOrders = List.of(
                new Order("1", "Jan", "Elektronika", 1000.0, today, true),
                new Order("2", "Anna", "Książki", 50.0, today, true),
                new Order("3", "Jan", "AGD", 5000.0, today, false), // false
                new Order("4", "Piotr", "Elektronika", 2000.0, LocalDate.of(2026, 10, 12), true),
                new Order("5", "Jan", "AGD", 1500.0, LocalDate.of(2026,10,14), true)
        );

        salesAnalyzer = new SalesAnalyzer(testOrders);
    }

    @Test
    void shouldCalculateTotalRevenueOnlyForCompletedOrders(){
        double totalRevenue = salesAnalyzer.calculateTotalRevenue();

        assertThat(totalRevenue)
                .as("Całkowity przychód powinien uwzględniać tylko zrealizowane zamówienia")
                .isEqualTo(5350.0);
    }
    @Test
    void shouldReturnTop3MostExpensiveCompletedOrders() {

        // When
        List<Order> topOrders = salesAnalyzer.getTop3MostExpensiveOrders();

        // Then
        assertThat(topOrders)
                .hasSize(3)
            .extracting(Order::id)
                .containsExactly("4","5","1");
    }

    @Test
    void shouldReturnOnlyOrdersFromToday(){
        LocalDate today = LocalDate.now();

        List<String> uniqueCustomers = salesAnalyzer.getUniqueCustomersByDate(today);
        assertThat(uniqueCustomers)
                .hasSize(2)
                .containsOnly("Jan","Anna");
    }
}
