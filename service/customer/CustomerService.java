package com.lihicouponsystem.service.customer;


import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;
import com.lihicouponsystem.web.dto.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDto> getCustomerByUuid(UUID customerUuid);

    Optional<CouponDto> purchaseCoupon(UUID customerUuid, UUID couponUuid);

    List<CouponDto> getAllCustomerCoupons(UUID customerUuid);

    List<CouponDto> getAllCustomerNotPurchasedCoupons(UUID customerUuid);

    List<CouponDto> getNearlyExpiredCustomerCoupons(UUID customerUuid);

    Optional<CustomerDto> updateDetails(UUID customerUuid, String email, String password);
}
