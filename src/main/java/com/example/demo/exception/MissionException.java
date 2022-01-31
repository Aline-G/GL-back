package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class MissionException extends FunctionalException{
    public MissionException(String message, HttpStatus status) {
        super(message,status);
    }
}
