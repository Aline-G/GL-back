package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class LineBillException extends FunctionalException{
    public LineBillException(String message, HttpStatus status) {
        super(message,status);
    }
}
