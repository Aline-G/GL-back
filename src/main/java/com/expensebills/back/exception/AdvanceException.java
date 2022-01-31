package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class AdvanceException extends FunctionalException{
    public AdvanceException(String message, HttpStatus status) {
        super(message,status);
    }
}
