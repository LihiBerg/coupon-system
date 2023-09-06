package com.lihicouponsystem.mapper.company;

import com.lihicouponsystem.entities.Company;
import com.lihicouponsystem.mapper.coupon.CouponMapper;
import com.lihicouponsystem.web.dto.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CompanyMapperImpl implements CompanyMapper {
    private final CouponMapper couponMapper;

    @Autowired
    public CompanyMapperImpl(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    @Override
    public Company map(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        return Company.builder().uuid(companyDto.getUuid()).name(companyDto.getName())
                .email(companyDto.getEmail()).password(companyDto.getPassword()).build();
    }

    @Override
    public CompanyDto map(Company company) {
        if (company == null) {
            return null;
        }
        return CompanyDto.builder().uuid(company.getUuid()).name(company.getName())
                .email(company.getEmail()).password(company.getPassword()).build();
    }

}