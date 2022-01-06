package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate parseDate(String date) {
        LocalDate parseDate;
        parseDate = LocalDate.parse(date, formatter);
        return parseDate;
    }
}
