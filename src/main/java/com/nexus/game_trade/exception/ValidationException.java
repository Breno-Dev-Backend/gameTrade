package com.nexus.game_trade.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException{

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "Validation Error");
    }
}
