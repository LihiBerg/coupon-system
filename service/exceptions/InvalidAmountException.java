package com.lihicouponsystem.service.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}