package com.lihicouponsystem.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {
    @JsonProperty("id")
    private UUID uuid;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    //  private List<CouponDto> coupons;
}
