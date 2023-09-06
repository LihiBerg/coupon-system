package com.lihicouponsystem.service.exceptions;

public class InvalidInsertionException extends RuntimeException {
    public InvalidInsertionException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

}