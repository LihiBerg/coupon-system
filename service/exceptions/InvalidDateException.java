package com.lihicouponsystem.service.exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

}