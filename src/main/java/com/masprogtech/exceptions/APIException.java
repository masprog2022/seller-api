package com.masprogtech.exceptions;

public class APIException extends RuntimeException {

    public APIException(){}


    public APIException(String message) {
        super(message);
    }
}
