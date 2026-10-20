package pl.mashiro_jz.salesanalyzer.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders") // Zmiana nazwy tabeli, bo "order" to słowo zastrzeżone w SQL!
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Baza danych sama będzie nadawać ID (1, 2, 3...)

    @Column(name = "csv_id")
    private String csvId; // Tutaj zachowamy stary identyfikator z Twojego pliku

    private String customerName;
    private String category;
    private double price;

    @Column(name = "order_date")
    private LocalDate date;

    private boolean isCompleted;

    // Hibernate wymaga pustego konstruktora
    public OrderEntity() {}

    // Konstruktor do wygodnego tworzenia obiektów
    public OrderEntity(String csvId, String customerName, String category, double price, LocalDate date, boolean isCompleted) {
        this.csvId = csvId;
        this.customerName = customerName;
        this.category = category;
        this.price = price;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCsvId() {
        return csvId;
    }

    public void setCsvId(String csvId) {
        this.csvId = csvId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}