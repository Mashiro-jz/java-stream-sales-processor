package pl.mashiro_jz.salesanalyzer.io;

import org.junit.jupiter.api.Test;
import pl.mashiro_jz.salesanalyzer.model.Order;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvOrderReaderTest {

    CsvOrderReader reader = new CsvOrderReader();

    @Test
    void shouldParseValidCsvData(){
        String csvContent = """
                id,customerName,category,price,date,isCompleted
                1,Jan,AGD,150.0,2026-10-15,true
                2,Anna,Książki,50.0,2026-10-15,false""";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));


        List<Order> result = reader.fetchOrdersFromCsvFile(inputStream);

        assertThat(result)
                .hasSize(2)
                .extracting(Order::customerName)
                .containsExactly("Jan", "Anna");
    }

    @Test
    void shouldReturnEmptyListWhenInputIsNull(){
        List<Order> result = reader.fetchOrdersFromCsvFile(null);

        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenDataFormatIsInvalid(){
        String csvContent = """
                id,customerName,category,price,date,isCompleted
                1,Jan,AGD,15.0,2026-10-15,true
                2,Anna,Książki,50.0,wczoraj,false""";

        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8));

        List<Order> result = reader.fetchOrdersFromCsvFile(inputStream);

        assertThat(result)
                .isEmpty();
    }
}
