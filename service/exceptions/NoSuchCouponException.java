package com.lihicouponsystem.service.exceptions;

public class NoSuchCouponException extends RuntimeException {
    public NoSuchCouponException(ExceptionMessage msg) {
        super(msg.getMessage());
    }

}