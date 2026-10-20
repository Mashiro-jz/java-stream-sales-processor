package pl.mashiro_jz.salesanalyzer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mashiro_jz.salesanalyzer.entity.OrderEntity;
import pl.mashiro_jz.salesanalyzer.io.CsvOrderReader;
import pl.mashiro_jz.salesanalyzer.model.Order;
import pl.mashiro_jz.salesanalyzer.repository.OrderRepository;

import java.io.InputStream;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final CsvOrderReader csvReader;
    private final OrderRepository orderRepository;

    public DatabaseInitializer(CsvOrderReader csvReader, OrderRepository orderRepository) {
        this.csvReader = csvReader;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(orderRepository.count() == 0){
            InputStream inputStream = getClass().getResourceAsStream("/orders.csv");
            List<Order> csvOrders = csvReader.fetchOrdersFromCsvFile(inputStream);

            List<OrderEntity> entitiesToSave = csvOrders.stream()
                    .map(order -> new OrderEntity(
                            order.id(),
                            order.customerName(),
                            order.category(),
                            order.price(),
                            order.date(),
                            order.isCompleted()
                    ))
                    .toList();

            orderRepository.saveAll(entitiesToSave);
            System.out.println("Załadowano " + entitiesToSave.size() + " zamówień do bazy H2!");
        }
    }
}
