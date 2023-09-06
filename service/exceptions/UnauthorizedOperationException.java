package com.lihicouponsystem.service.exceptions;

public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}