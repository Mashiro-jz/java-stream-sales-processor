package pl.mashiro_jz.salesanalyzer;


import pl.mashiro_jz.salesanalyzer.model.Order;
import pl.mashiro_jz.salesanalyzer.service.SalesAnalyzer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main() {

        // 1. Przygotowanie danych testowych
        LocalDate dzisiaj = LocalDate.now();
        LocalDate wczoraj = dzisiaj.minusDays(1);

        SalesAnalyzer analyzer = getSalesAnalyzer(dzisiaj, wczoraj);

        // 3. Testowanie logiki biznesowej
        System.out.println("--- 1. CAŁKOWITY PRZYCHÓD ---");
        System.out.println("Oczekiwany wynik: 10335.0");
        System.out.println("Wynik z metody:   " + analyzer.calculateTotalRevenue());
        System.out.println();

        System.out.println("--- 2. TOP 3 NAJDROŻSZE (ZREALIZOWANE) ZAMÓWIENIA ---");
        List<Order> topOrders = analyzer.getTop3MostExpensiveOrders();
        topOrders.forEach(order -> System.out.println(order.id() + " - " + order.price() + " PLN"));
        System.out.println();

        System.out.println("--- 3. UNIKALNI KLIENCI Z DZISIAJ (" + dzisiaj + ") ---");
        // Jan Kowalski kupił dwie rzeczy, ale powinien pojawić się na liście tylko raz!
        List<String> uniqueCustomers = analyzer.getUniqueCustomersByDate(dzisiaj);
        System.out.println(uniqueCustomers);
        System.out.println();

        System.out.println("--- 4. ZAMÓWIENIA POGRUPOWANE PO KATEGORII ---");
        Map<String, List<Order>> groupedByCategory = analyzer.groupOrdersByCategory();
        groupedByCategory.forEach((kategoria, listaZamo) -> System.out.println(kategoria + ": " + listaZamo.size() + " zamówień"));
        System.out.println();

        System.out.println("--- 5. PRZYCHÓD W KATEGORIACH (TYLKO ZREALIZOWANE) ---");
        Map<String, Double> revenuePerCategory = analyzer.calculateRevenuePerCategory();
        revenuePerCategory.forEach((kategoria, przychod) -> System.out.println(kategoria + ": " + przychod + " PLN"));
    }

    private static SalesAnalyzer getSalesAnalyzer(LocalDate dzisiaj, LocalDate wczoraj) {
        List<Order> orders = List.of(
                // Elektronika
                new Order("1", "Jan Kowalski", "Elektronika", 3500.0, dzisiaj, true),
                new Order("2", "Anna Nowak", "Elektronika", 1200.0, dzisiaj, true),
                new Order("3", "Piotr Wiśniewski", "Elektronika", 4000.0, wczoraj, false), // Niezrealizowane
                new Order("4", "Katarzyna Wójcik", "Elektronika", 800.0, wczoraj, true),
                new Order("5", "Jan Kowalski", "Elektronika", 150.0, dzisiaj, true), // Ten sam klient, ten sam dzień

                // Książki
                new Order("6", "Michał Kaczmarek", "Książki", 45.0, dzisiaj, true),
                new Order("7", "Agnieszka Mazur", "Książki", 80.0, wczoraj, true),
                new Order("8", "Tomasz Krawczyk", "Książki", 120.0, dzisiaj, false), // Niezrealizowane
                new Order("9", "Anna Nowak", "Książki", 60.0, dzisiaj, true),
                new Order("10", "Piotr Wiśniewski", "Książki", 50.0, dzisiaj, true),

                // AGD
                new Order("11", "Krzysztof Krol", "AGD", 1500.0, dzisiaj, true),
                new Order("12", "Maria Zając", "AGD", 2200.0, wczoraj, true),
                new Order("13", "Michał Kaczmarek", "AGD", 800.0, wczoraj, false), // Niezrealizowane
                new Order("14", "Jan Kowalski", "AGD", 300.0, dzisiaj, true),
                new Order("15", "Agnieszka Mazur", "AGD", 450.0, dzisiaj, true)
        );

        return new SalesAnalyzer(orders);
    }
}