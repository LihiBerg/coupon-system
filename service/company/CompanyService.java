package com.lihicouponsystem.service.company;

import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyService {
    Optional<CompanyDto> getCompanyByUuid(UUID companyUuid);

    Optional<CouponDto> addCoupon(UUID companyUuid, CouponDto couponDto);

    void deleteCouponByUuid(UUID companyUuid, UUID couponUuid);

    Optional<CouponDto> updateAmount(UUID companyUuid, UUID couponUuid, Integer amount);

    List<CouponDto> getAllCompanyCoupons(UUID companyUuid);

    Optional<CompanyDto> updateDetails(UUID companyUuid, String email, String password);

}
