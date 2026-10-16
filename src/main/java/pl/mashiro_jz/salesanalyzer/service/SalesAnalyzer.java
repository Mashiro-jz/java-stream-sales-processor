package pl.mashiro_jz.salesanalyzer.service;

import org.springframework.stereotype.Service;
import pl.mashiro_jz.salesanalyzer.io.CsvOrderReader;
import pl.mashiro_jz.salesanalyzer.model.Order;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serwis analizujący dane sprzedażowe.
 * Wykorzystuje Stream API do agregacji, filtrowania i grupowania zamówień.
 *
 * @author Mashiro_jz
 */
@Service
public class SalesAnalyzer {
    private final List<Order> orderList;

    /**
     * Tworzy instancję analizatora na podstawie przekazanej listy zamówień.
     *
     * @param reader, za pomocą którego aplikacja otrzymuje dane do analizy
     */
    public SalesAnalyzer(CsvOrderReader reader) {
        InputStream inputStream = getClass().getResourceAsStream("/orders.csv");
        this.orderList = reader.fetchOrdersFromCsvFile(inputStream);
    }

    /**
     * Oblicza całkowity przychód wygenerowany przez sklep.
     * Sumuje wartości wyłącznie dla zrealizowanych zamówień.
     *
     * @return suma przychodów ze zrealizowanych zamówień
     */
    public double calculateTotalRevenue(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .mapToDouble(Order::price)
                .sum();
    }

    /**
     * Wyszukuje najbardziej wartościowe transakcje w historii.
     * Pod uwagę brane są tylko zrealizowane zamówienia.
     *
     * @return lista zawierająca maksymalnie 3 najdroższe obiekty {@link Order}, posortowane malejąco po cenie
     */
    public List<Order> getTop3MostExpensiveOrders(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .sorted(Comparator.comparing(Order::price).reversed())
                .limit(3)
                .toList();
    }

    /**
     * Zwraca zestawienie unikalnych klientów (bez duplikatów), którzy złożyli jakiekolwiek
     * zamówienie w określonym dniu roboczym.
     *
     * @param date data, dla której przeprowadzana jest analiza aktywności klientów
     * @return lista unikalnych imion i nazwisk klientów
     */
    public List<String> getUniqueCustomersByDate(LocalDate date){
        return orderList.stream()
                .filter(order -> order.date().equals(date))
                .map(Order::customerName)
                .distinct() // usuwa duplikaty
                .toList();
    }

    /**
     * Kategoryzuje całą historię zamówień przypisując je do odpowiednich działów produktowych.
     * Pod uwagę brane są wszystkie zamówienia (zarówno zrealizowane, jak i odrzucone).
     *
     * @return mapa, w której kluczem jest nazwa kategorii, a wartością lista przypisanych do niej zamówień
     */
    public Map<String, List<Order>> groupOrdersByCategory(){
        return orderList.stream()
                .collect(Collectors.groupingBy(
                        Order::category
                ));
    }

    /**
     * Przygotowuje raport finansowy z podziałem na działy sklepu.
     * Oblicza łączny przychód dla każdej kategorii, biorąc pod uwagę wyłącznie zrealizowane zamówienia.
     *
     * @return mapa, w której kluczem jest nazwa kategorii, a wartością suma uzyskanych w niej przychodów
     */
    public Map<String, Double> calculateRevenuePerCategory(){
        return orderList.stream()
                .filter(Order::isCompleted)
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.summingDouble(Order::price)
                ));
    }
}