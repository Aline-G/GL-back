package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class TeamException extends FunctionalException{
    public TeamException(String message, HttpStatus status) {
        super(message,status);
    }
}
