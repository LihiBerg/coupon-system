package com.lihicouponsystem.web.controller;

import com.lihicouponsystem.config.AppConfiguration;
import com.lihicouponsystem.service.company.CompanyService;
import com.lihicouponsystem.service.exceptions.ExceptionMessage;
import com.lihicouponsystem.service.exceptions.InvalidLoginException;
import com.lihicouponsystem.web.ClientSession;
import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
@CrossOrigin("http://localhost:3000")
public class CompanyController {
    private final CompanyService companyService;
    private final Map<String, ClientSession> tokensMap;
    private final AppConfiguration configuration;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{token}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.of(companyService.getCompanyByUuid(companyUuid));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/{token}")
    public ResponseEntity<CouponDto> insert(@RequestBody CouponDto couponDto,
                                            @PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.of(companyService.addCoupon(companyUuid, couponDto));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{token}")
    public void delete(@RequestParam(name = "uuid") UUID couponUuid, @PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        companyService.deleteCouponByUuid(companyUuid, couponUuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update-amount/{token}")
    public ResponseEntity<CouponDto> updateAmount(@RequestParam(name = "uuid") UUID couponUuid,
                                                  @RequestParam(name = "amount") Integer amount,
                                                  @PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.of(companyService.updateAmount(companyUuid, couponUuid, amount));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/update-account-settings/{token}")
    public ResponseEntity<CompanyDto> updateDetails(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "password")
                                                    String password, @PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return ResponseEntity.of(companyService.updateDetails(companyUuid, email, password));
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("all/{token}")
    public List<CouponDto> allCompanyCoupons(@PathVariable String token) {
        UUID companyUuid = getUuidFromToken(token);
        return companyService.getAllCompanyCoupons(companyUuid);
    }

    public UUID getUuidFromToken(String token) {
        ClientSession clientSession = tokensMap.get(token);

        if (clientSession == null) {
            throw new InvalidLoginException(ExceptionMessage.NO_SUCH_TOKEN);
        }
        if ((System.currentTimeMillis() - clientSession.getLastAccessedMillis()) >
                Long.valueOf(configuration.getExpirationDuration())) {//Long.valueOf = solve bug
            throw new InvalidLoginException(ExceptionMessage.TOKEN_EXPIRED);
        }
        if (clientSession.getType() != LoginType.COMPANY) {
            throw new InvalidLoginException(ExceptionMessage.WRONG_TYPE);
        }
        clientSession.updateLastAccessedMillis();
        return clientSession.getUuid();
    }
}
