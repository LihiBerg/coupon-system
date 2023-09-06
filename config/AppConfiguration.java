package com.lihicouponsystem.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@Setter
@ToString
@Configuration
@EnableAsync
@EnableScheduling
@ConfigurationProperties(prefix = "token")
public class AppConfiguration {
    private Long expirationDuration;

}
