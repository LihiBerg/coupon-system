package com.lihicouponsystem.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

//todo: added
@Data
@Builder
public class TokenDto {
    @JsonProperty("token")
    private String token;
}
