package com.lihicouponsystem.web.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponSystemErrorResponse {

    private final String timeStamp;
    private final String message;

    private static CouponSystemErrorResponse ofNow(String message) {
        return new CouponSystemErrorResponse(millisToDate(System.currentTimeMillis()), message);
    }

    public static CouponSystemErrorResponse invalidLogin(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse invalidAmount(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse invalidDate(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse invalidInsertion(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse invalidPrice(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse noSuchCompany(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse noSuchCoupon(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse noSuchCustomer(String message) {
        return ofNow(message);
    }

    public static CouponSystemErrorResponse unauthorizedOperation(String message) {
        return ofNow(message);
    }

    public static String millisToDate(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(timeInMillis));
    }
}
