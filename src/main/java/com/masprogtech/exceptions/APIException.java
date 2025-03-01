package com.masprogtech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}

