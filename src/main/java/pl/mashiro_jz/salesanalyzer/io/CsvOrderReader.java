package pl.mashiro_jz.salesanalyzer.io;

import org.springframework.stereotype.Service;
import pl.mashiro_jz.salesanalyzer.model.Order;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * Klasa narzędziowa odpowiedzialna za odczyt i parsowanie danych z plików CSV.
 *
 * @author Mashiro_jz
 */
@Service
public class CsvOrderReader {

    /**
     * Wczytuje i parsuje dane o zamówieniach ze wskazanego strumienia danych (w formacie CSV).
     * <p>
     * Metoda pomija pierwszy wiersz (nagłówki) i w przypadku napotkania błędów
     * wejścia/wyjścia lub błędnego formatu danych, bezpiecznie zwraca pustą listę.
     * </p>
     *
     * @param filename strumień wejściowy zawierający dane CSV (nie może być {@code null})
     * @return lista przetworzonych obiektów typu {@link Order}, lub pusta lista w przypadku wystąpienia błędu
     */
    public List<Order> fetchOrdersFromCsvFile(InputStream filename){
        List<Order> orders;

        if(filename == null)
            return List.of();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filename))){
            Stream<String> lines = reader.lines();
            orders = lines.skip(1).map(this::parseLine).toList();
        } catch (IOException e) {
            System.out.println("Wystąpił błąd wprowadzania pliku: " + e.fillInStackTrace());
            return List.of();
        }
        catch (RuntimeException e) {
            System.out.println("Plik zawiera uszkodzone dane: " + e.fillInStackTrace());
            return List.of();
        } catch (Exception e) {
            System.out.println("Wystąpił błąd:" + e.fillInStackTrace());
            return List.of();
        }
        return orders;
    }

    /**
     * Parsuje pojedynczą linię tekstu w formacie CSV na obiekt zamówienia.
     *
     * @param line pojedynczy wiersz z pliku CSV zawierający dane rozdzielone przecinkami
     * @return instancja klasy {@link Order} reprezentująca przetworzone dane
     * @throws NumberFormatException jeśli format ceny jest nieprawidłowy
     * @throws java.time.format.DateTimeParseException jeśli format daty jest nieprawidłowy
     */
    private Order parseLine(String line){
        List<String> lineList = List.of(line.split(","));
        return new Order(lineList.getFirst(), lineList.get(1), lineList.get(2), Double.parseDouble(lineList.get(3)), LocalDate.parse(lineList.get(4)), Boolean.parseBoolean(lineList.getLast()));
    }
}