package com.expensebills.back.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends FunctionalException{
    public UserException(String message, HttpStatus status) {
        super(message,status);
    }
}
