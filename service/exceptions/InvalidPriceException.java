package com.lihicouponsystem.service.exceptions;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

}