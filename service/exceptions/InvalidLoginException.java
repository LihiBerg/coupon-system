package com.lihicouponsystem.service.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(ExceptionMessage msg) {
        super(msg.getMessage());
    }
}