package com.expensebills.back.exception;

import org.springframework.http.HttpStatus;

public class MissionException extends FunctionalException{
    public MissionException(String message, HttpStatus status) {
        super(message,status);
    }
}
