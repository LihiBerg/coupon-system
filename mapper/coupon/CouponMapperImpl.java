package com.lihicouponsystem.mapper.coupon;

import com.lihicouponsystem.entities.Coupon;
import com.lihicouponsystem.web.dto.CouponDto;
import org.springframework.stereotype.Component;


@Component
public class CouponMapperImpl implements CouponMapper {
    @Override
    public Coupon map(CouponDto couponDto) {
        if (couponDto == null) {
            return null;
        }
        return Coupon.builder().uuid(couponDto.getUuid()).category(couponDto.getCategory())
                .title(couponDto.getTitle()).description(couponDto.getDescription())
                .startDate(couponDto.getStartDate()).endDate(couponDto.getEndDate())
                .amount(couponDto.getAmount()).price(couponDto.getPrice())
                .imageUrl(couponDto.getImageUrl()).build();
    }

    @Override
    public CouponDto map(Coupon coupon) {
        if (coupon == null) {
            return null;
        }
        return CouponDto.builder().uuid(coupon.getUuid()).category(coupon.getCategory())
                .title(coupon.getTitle()).description(coupon.getDescription())
                .startDate(coupon.getStartDate()).endDate(coupon.getEndDate())
                .amount(coupon.getAmount()).price(coupon.getPrice()).imageUrl(coupon.getImageUrl())
                .build();

    }
}

