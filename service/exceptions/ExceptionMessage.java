package com.lihicouponsystem.service.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    INCORRECT_EMAIL_OR_PASSWORD("Incorrect Username Or Password. Please Retry"),
    INVALID_AMOUNT_TO_PURCHASE("Invalid Amount Value. No Coupons Left. Purchase Failed"),
    INVALID_INSERTION("Coupon's Already Exists! Insertion Failed."),
    NO_SUCH_CUSTOMER("Customer Not Found! Operation Failed."),
    NO_SUCH_COMPANY("Company Not Found! Operation Failed."),
    DOUBLE_PURCHASE("You Have Already Purchased This Type Of Coupon In The Past! " +
                    "Double Purchase Is Prohibited."),
    NO_SUCH_COUPON("Coupon Not Found! Operation Failed."),
    INVALID_AMOUNT("Invalid Amount Value. Please Make Sure To Insert A Proper Amount Value"),
    INVALID_PRICE("Invalid Price Value. Please Make Sure To Insert A Proper Price Value"),
    NO_SUCH_TOKEN("No Such Token. Try Again."),
    TOKEN_EXPIRED("Token Expired. Try To Login Again"),
    INVALID_DATE("Invalid Date Value. Please Make Sure To Insert A Proper Date Value"),
    UNAUTHORIZED("Unauthorized To Perform This Action!"),
    WRONG_TYPE("Inappropriate Type"),

    //todo:added

    INVALID_PASSWORD_LENGTH("Invalid Password Length. Please Make Sure It Contains At Least" +
            " 8 Characters "),
    INVALID_PASSWORD("Invalid Password. Please Make Sure It Contains: At Least 1 Capital " +
            "Letter At Least 1 Small Letter And At Least 1 Number."),
    INVALID_EMAIL_OR_PASSWORD_UPDATE("Email Or Password Can't Be Found! Operation Failed."),
    INVALID_EMAIL("No Such Email Address. Try Again ");

    final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

}
