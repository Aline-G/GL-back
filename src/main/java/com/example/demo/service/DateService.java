package com.example.demo.service;

import com.example.demo.exception.DateException;
import org.springframework.http.HttpStatus;
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

    public void isCoherent(LocalDate date1) throws DateException {
        LocalDate date = LocalDate.now();
        if(date.isAfter(date1)){
            throw new DateException("The date of the bill is passed", HttpStatus.BAD_REQUEST);
        }
    }
}
