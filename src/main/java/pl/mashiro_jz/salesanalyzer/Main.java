package pl.mashiro_jz.salesanalyzer;


import pl.mashiro_jz.salesanalyzer.io.CsvOrderReader;
import pl.mashiro_jz.salesanalyzer.model.Order;
import pl.mashiro_jz.salesanalyzer.service.SalesAnalyzer;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main() {

        // 1. Przygotowanie danych testowych
        LocalDate dzisiaj = LocalDate.now();

        InputStream filename = Main.class.getResourceAsStream("/orders.csv");
        SalesAnalyzer analyzer = getSalesAnalyzer(filename);

        // 3. Testowanie logiki biznesowej
        System.out.println("--- 1. CAŁKOWITY PRZYCHÓD ---");
        System.out.println("Oczekiwany wynik: 3550.0");
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

    private static SalesAnalyzer getSalesAnalyzer(InputStream filename) {
        CsvOrderReader csvOrderReader = new CsvOrderReader();
        List<Order> orders = csvOrderReader.fetchOrdersFromCsvFile(filename);

        return new SalesAnalyzer(orders);
    }
}