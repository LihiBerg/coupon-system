package com.lihicouponsystem.service.coupon;

import com.lihicouponsystem.web.dto.CouponDto;

import java.util.List;
import java.util.UUID;

public interface CouponService {
    List<CouponDto> getAllCoupons();
}
