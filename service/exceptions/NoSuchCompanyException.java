package com.lihicouponsystem.service.exceptions;

public class NoSuchCompanyException extends RuntimeException {
    public NoSuchCompanyException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

    public NoSuchCompanyException() {
        
    }
}