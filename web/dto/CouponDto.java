package com.lihicouponsystem.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lihicouponsystem.entities.Category;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@ToString
@Data
@Builder
public class CouponDto {
    @JsonProperty("id")
    private UUID uuid;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer amount;
    private BigDecimal price;
    private String imageUrl;
}
