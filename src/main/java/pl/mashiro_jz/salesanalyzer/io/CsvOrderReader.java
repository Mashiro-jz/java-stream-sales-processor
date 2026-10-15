package pl.mashiro_jz.salesanalyzer.io;

import pl.mashiro_jz.salesanalyzer.model.Order;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class CsvOrderReader {
    public List<Order> fetchOrdersFromCsvFile(InputStream filename){
        List<Order> orders;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filename))){
            Stream<String> lines = reader.lines();
            orders = lines.skip(1).map(this::parseLine).toList();
        } catch (IOException e) {
            System.out.println("Wystąpił błąd: " + e.getMessage());
            return List.of();
        }
        return orders;
    }
    private Order parseLine(String line){
        List<String> lineList = List.of(line.split(","));
        return new Order(lineList.getFirst(),lineList.get(1), lineList.get(2), Double.parseDouble(lineList.get(3)), LocalDate.parse(lineList.get(4)), Boolean.parseBoolean(lineList.getLast()));
    }
}
