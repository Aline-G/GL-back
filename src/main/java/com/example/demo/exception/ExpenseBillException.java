package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ExpenseBillException extends FunctionalException{
    public ExpenseBillException(String message, HttpStatus status) {
        super(message,status);
    }
}
