package com.lihicouponsystem.service.exceptions;

public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

}