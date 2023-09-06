package com.lihicouponsystem.web.controller;

import com.lihicouponsystem.service.coupon.CouponService;
import com.lihicouponsystem.web.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class CouponController {
    private final CouponService couponService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coupon")
    public List<CouponDto> allCoupons() {
        return couponService.getAllCoupons();
    }
}
