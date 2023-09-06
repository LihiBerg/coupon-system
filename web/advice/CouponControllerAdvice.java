package com.lihicouponsystem.web.advice;

import com.lihicouponsystem.service.exceptions.*;
import com.lihicouponsystem.web.response.CouponSystemErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler({InvalidAmountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleInvalidAmount(RuntimeException ex) {
        return CouponSystemErrorResponse.invalidAmount(ex.getMessage());
    }

    @ExceptionHandler({InvalidDateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleInvalidDate(RuntimeException ex) {
        return CouponSystemErrorResponse.invalidDate(ex.getMessage());
    }

    @ExceptionHandler({InvalidInsertionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleInvalidInsertion(RuntimeException ex) {
        return CouponSystemErrorResponse.invalidInsertion(ex.getMessage());
    }

    @ExceptionHandler({InvalidLoginException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CouponSystemErrorResponse handleInvalidLogin(RuntimeException ex) {
        return CouponSystemErrorResponse.invalidLogin(ex.getMessage());
    }

    @ExceptionHandler({InvalidPriceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleInvalidPrice(RuntimeException ex) {
        return CouponSystemErrorResponse.invalidPrice(ex.getMessage());
    }

    @ExceptionHandler({NoSuchCompanyException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleNoSuchCompany(RuntimeException ex) {
        return CouponSystemErrorResponse.noSuchCompany(ex.getMessage());
    }

    @ExceptionHandler({NoSuchCouponException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleNoSuchCoupon(RuntimeException ex) {
        return CouponSystemErrorResponse.noSuchCoupon(ex.getMessage());
    }

    @ExceptionHandler({NoSuchCustomerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CouponSystemErrorResponse handleNoSuchCustomer(RuntimeException ex) {
        return CouponSystemErrorResponse.noSuchCustomer(ex.getMessage());
    }

    @ExceptionHandler({UnauthorizedOperationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CouponSystemErrorResponse handleUnauthorizedOperation(RuntimeException ex) {
        return CouponSystemErrorResponse.unauthorizedOperation(ex.getMessage());
    }
}
