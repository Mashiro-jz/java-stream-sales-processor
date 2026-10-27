package pl.mashiro_jz.salesanalyzer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mashiro_jz.salesanalyzer.dto.Order;
import pl.mashiro_jz.salesanalyzer.service.SalesAnalyzer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Analizator Sprzedaży", description = "Endpointy do pobierania raportów i analizowania danych e-commerce z bazy danych")
public class SalesController {

    private final SalesAnalyzer analyzer;

    public SalesController(SalesAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @Operation(
            summary = "Pobierz całkowity przychód",
            description = "Oblicza i zwraca sumę przychodów ze wszystkich zrealizowanych zamówień w systemie."
    )
    @GetMapping("/revenue")
    public double getRevenue() {
        return analyzer.calculateTotalRevenue();
    }

    @Operation(
            summary = "Pobierz top 3 zamówień",
            description = "Zwraca listę maksymalnie trzech najdroższych, zrealizowanych zamówień."
    )
    @GetMapping("/top-orders")
    public List<Order> getTopOrders() {
        return analyzer.getTop3MostExpensiveOrders();
    }

    @Operation(
            summary = "Grupuj zamówienia po kategoriach",
            description = "Zwraca mapę, gdzie kluczem jest kategoria produktu, a wartością lista przypisanych do niej zamówień."
    )
    @GetMapping("/categories")
    public Map<String, List<Order>> getOrdersByCategory() {
        return analyzer.groupOrdersByCategory();
    }

    @Operation(
            summary = "Przychód z podziałem na kategorie",
            description = "Oblicza łączny przychód ze zrealizowanych zamówień dla każdej kategorii z osobna."
    )
    @GetMapping("/categories/revenue")
    public Map<String, Double> getRevenueByCategory(){
        return analyzer.calculateRevenuePerCategory();
    }

    @Operation(
            summary = "Unikalni klienci w danym dniu",
            description = "Zwraca listę unikalnych nazwisk klientów, którzy złożyli zamówienia w wskazanym dniu."
    )
    @GetMapping("/customers")
    public List<String> getCustomers(
            @Parameter(description = "Data w formacie RRRR-MM-DD, np. 2026-10-15", example = "2026-10-15")
            @RequestParam("date") LocalDate date
    ) {
        return analyzer.getUniqueCustomersByDate(date);
    }
}