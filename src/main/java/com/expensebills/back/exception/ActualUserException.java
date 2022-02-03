package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class ActualUserException extends FunctionalException{
    public ActualUserException(String message, HttpStatus status) {
        super(message,status);
    }
}

