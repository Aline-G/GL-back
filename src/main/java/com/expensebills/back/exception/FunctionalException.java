package com.expensebills.back.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class FunctionalException extends Exception {
    private final HttpStatus status;

    public FunctionalException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
