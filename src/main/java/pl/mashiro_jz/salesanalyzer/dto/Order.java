package pl.mashiro_jz.salesanalyzer.dto;

import java.time.LocalDate;

public record Order(String id, String customerName, String category, double price, LocalDate date, boolean isCompleted) {
}