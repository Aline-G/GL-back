package com.expensebills.back.service;

import com.expensebills.back.exception.DateException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void checkDateMission(LocalDate dateBegining, LocalDate dateEnding) throws DateException {
        System.out.println(dateBegining.isAfter(dateEnding));
        System.out.println(dateEnding.isAfter(LocalDate.now()));
        if(dateBegining.isAfter(dateEnding)){
            throw new DateException("Beginning date is after ending date", HttpStatus.BAD_REQUEST);
        }
        else if(dateEnding.isBefore(LocalDate.now())){
            throw new DateException("Date of mission is before today ", HttpStatus.BAD_REQUEST);
        }
    }

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
