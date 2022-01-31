package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class LineBillException extends FunctionalException{
    public LineBillException(String message, HttpStatus status) {
        super(message,status);
    }
}
