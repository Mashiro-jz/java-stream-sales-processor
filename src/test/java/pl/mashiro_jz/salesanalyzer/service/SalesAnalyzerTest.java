package pl.mashiro_jz.salesanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mashiro_jz.salesanalyzer.entity.OrderEntity;
import pl.mashiro_jz.salesanalyzer.dto.Order;
import pl.mashiro_jz.salesanalyzer.repository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesAnalyzerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private SalesAnalyzer salesAnalyzer;

    private LocalDate today;
    private List<OrderEntity> completedOrders;
    private List<OrderEntity> todayOrders;

    @BeforeEach
    void setUp() {
        today = LocalDate.now();

        completedOrders = List.of(
                new OrderEntity("1", "Jan", "Elektronika", 1000.0, today, true),
                new OrderEntity("2", "Anna", "Książki", 50.0, today, true),
                new OrderEntity("4", "Piotr", "Elektronika", 2000.0, LocalDate.of(2026, 10, 12), true),
                new OrderEntity("5", "Jan", "AGD", 1500.0, LocalDate.of(2026, 10, 14), true)
        );

        todayOrders = List.of(
                new OrderEntity("1", "Jan", "Elektronika", 1000.0, today, true),
                new OrderEntity("2", "Anna", "Książki", 50.0, today, true),
                new OrderEntity("3", "Jan", "AGD", 5000.0, today, false)
        );
    }

    @Test
    void shouldCalculateTotalRevenueOnlyForCompletedOrders() {
        when(orderRepository.findByIsCompletedTrue()).thenReturn(completedOrders);

        double totalRevenue = salesAnalyzer.calculateTotalRevenue();

        assertThat(totalRevenue)
                .as("Całkowity przychód powinien uwzględniać tylko zrealizowane zamówienia")
                .isEqualTo(4550.0); // 1000 + 50 + 2000 + 1500
    }

    @Test
    void shouldReturnTop3MostExpensiveCompletedOrders() {
        when(orderRepository.findByIsCompletedTrue()).thenReturn(completedOrders);

        List<Order> topOrders = salesAnalyzer.getTop3MostExpensiveOrders();

        assertThat(topOrders)
                .hasSize(3)
                .extracting(Order::id)
                .containsExactly("4", "5", "1");
    }

    @Test
    void shouldReturnOnlyOrdersFromToday() {
        when(orderRepository.findByDate(today)).thenReturn(todayOrders);

        List<String> uniqueCustomers = salesAnalyzer.getUniqueCustomersByDate(today);

        assertThat(uniqueCustomers)
                .hasSize(2)
                .containsOnly("Jan", "Anna");
    }
}