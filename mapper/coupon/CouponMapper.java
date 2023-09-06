package com.lihicouponsystem.mapper.coupon;

import com.lihicouponsystem.entities.Coupon;
import com.lihicouponsystem.web.dto.CouponDto;

public interface CouponMapper {
    Coupon map(CouponDto couponDto);

    CouponDto map(Coupon coupon);
}
