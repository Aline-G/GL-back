package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class ExpenseBillException extends FunctionalException{
    public ExpenseBillException(String message, HttpStatus status) {
        super(message,status);
    }
}
