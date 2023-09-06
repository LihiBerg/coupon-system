package com.lihicouponsystem.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CompanyDto {
    @JsonProperty("id")
    private UUID uuid;
    private String name;
    private String email;
    private String password;
    // private List<CouponDto> coupons;
}
