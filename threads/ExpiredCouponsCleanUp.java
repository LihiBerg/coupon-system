package com.lihicouponsystem.threads;

import com.lihicouponsystem.entities.Coupon;
import com.lihicouponsystem.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpiredCouponsCleanUp {
    private final CouponRepository couponRepo;

    @Autowired
    public ExpiredCouponsCleanUp(CouponRepository couponRepo) {
        this.couponRepo = couponRepo;
    }

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void run() {
        List<Coupon> allCoupons = couponRepo.findAll();
        List<Coupon> expiredCoupons = allCoupons.stream()
                .filter(coupon -> coupon.getEndDate().before(new Date(System.currentTimeMillis())))
                .collect(Collectors.toList());

        couponRepo.deleteAll(expiredCoupons);
    }
}
