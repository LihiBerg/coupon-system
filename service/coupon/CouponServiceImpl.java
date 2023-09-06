package com.lihicouponsystem.service.coupon;

import com.lihicouponsystem.mapper.coupon.CouponMapper;
import com.lihicouponsystem.repositories.CouponRepository;
import com.lihicouponsystem.web.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public List<CouponDto> getAllCoupons() {
        return couponRepository.findAll().stream().map(couponMapper::map).collect(Collectors.toList());

    }
}
