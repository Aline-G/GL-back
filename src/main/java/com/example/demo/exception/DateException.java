package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class DateException extends FunctionalException{
    public DateException(String message, HttpStatus status) {
        super(message,status);
    }
}
