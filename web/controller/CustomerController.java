package com.lihicouponsystem.web.controller;

import com.lihicouponsystem.config.AppConfiguration;
import com.lihicouponsystem.service.customer.CustomerService;
import com.lihicouponsystem.service.exceptions.ExceptionMessage;
import com.lihicouponsystem.service.exceptions.InvalidLoginException;
import com.lihicouponsystem.web.ClientSession;
import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;
import com.lihicouponsystem.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin("http://localhost:3000")
public class CustomerController {
    private final AppConfiguration configuration;
    private final CustomerService customerService;
    private final Map<String, ClientSession> tokensMap;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{token}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.of(customerService.getCustomerByUuid(customerUuid));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchase/{token}")
    public ResponseEntity<CouponDto> insert(@RequestParam(name = "uuid") UUID couponUuid,
                                            @PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.of(customerService.purchaseCoupon(customerUuid, couponUuid));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/{token}")
    public List<CouponDto> allCustomerCoupons(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return this.customerService.getAllCustomerCoupons(customerUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/not-purchased/{token}")
    public List<CouponDto> customerNotPurchasedCoupons(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return this.customerService.getAllCustomerNotPurchasedCoupons(customerUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/nearly-expired/{token}")
    public List<CouponDto> customerNearlyExpiredCoupons(@PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return this.customerService.getNearlyExpiredCustomerCoupons(customerUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update-account-settings/{token}")
    public ResponseEntity<CustomerDto> updateDetails(@RequestParam(name = "email") String email,
                                                     @RequestParam(name = "password")
                                                     String password, @PathVariable String token) {
        UUID customerUuid = getUuidFromToken(token);
        return ResponseEntity.of(customerService.updateDetails(customerUuid, email, password));
    }


    public UUID getUuidFromToken(String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null) {
            throw new InvalidLoginException(ExceptionMessage.NO_SUCH_TOKEN);
        }
        if ((System.currentTimeMillis() - clientSession.getLastAccessedMillis()) >
                Long.valueOf(configuration.getExpirationDuration())) { //Long.valueOf = solve bug
            throw new InvalidLoginException(ExceptionMessage.TOKEN_EXPIRED);
        }

        if (clientSession.getType() != LoginType.CUSTOMER) {
            throw new InvalidLoginException(ExceptionMessage.WRONG_TYPE);
        }
        clientSession.updateLastAccessedMillis();
        return clientSession.getUuid();
    }

}
