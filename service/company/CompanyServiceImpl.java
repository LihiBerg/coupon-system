package com.lihicouponsystem.service.company;

import com.lihicouponsystem.entities.Company;
import com.lihicouponsystem.entities.Coupon;
import com.lihicouponsystem.mapper.company.CompanyMapper;
import com.lihicouponsystem.mapper.coupon.CouponMapper;
import com.lihicouponsystem.repositories.CompanyRepository;
import com.lihicouponsystem.repositories.CouponRepository;
import com.lihicouponsystem.service.ValidationCheck;
import com.lihicouponsystem.service.exceptions.*;
import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CompanyMapper companyMapper;
    private final CouponMapper couponMapper;
    private static final Integer MIN_VALUE = 1;
    private final ValidationCheck check;


    public Optional<CompanyDto> getCompanyByUuid(UUID companyUuid) {
        return companyRepository.findByUuid(companyUuid).map(companyMapper::map);
    }

    @Override
    public Optional<CouponDto> addCoupon(UUID companyUuid, CouponDto couponDto) {
        if (couponDto.getAmount() == null || couponDto.getAmount() < MIN_VALUE) {
            throw new InvalidAmountException(ExceptionMessage.INVALID_AMOUNT);
        }

        if (couponDto.getPrice() == null || couponDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException(ExceptionMessage.INVALID_PRICE);
        }

        List<Coupon> allCoupons = couponRepository.findAll();
        for (Coupon currentCoupon : allCoupons) {
            if ((currentCoupon.getTitle().equalsIgnoreCase(couponDto.getTitle())) ||
                    (currentCoupon.getUuid().equals(couponDto.getUuid()))) {
                throw new InvalidInsertionException(ExceptionMessage.INVALID_INSERTION);
            }
        }
        Optional<Company> optCompany = companyRepository.findByUuid(companyUuid);
        if (optCompany.isPresent()) {
            Company company = optCompany.get();
            UUID uuid = couponDto.getUuid() != null ? couponDto.getUuid() : UUID.randomUUID();
            Coupon coupon = couponMapper.map(couponDto);
            coupon.setUuid(uuid);
            coupon.setCompany(company);
            return Optional.of(couponMapper.map(couponRepository.save(coupon)));
        }
        throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY);
    }

    @Override
    @Transactional
    public void deleteCouponByUuid(UUID companyUuid, UUID couponUuid) {
        Company company =
                companyRepository.findByUuid(companyUuid).orElseThrow(NoSuchCompanyException::new);
        Optional<Coupon> optCoupon = couponRepository.findByUuid(couponUuid);
        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException(ExceptionMessage.NO_SUCH_COUPON);
        }
        List<Coupon> allCompanyCoupons = company.getCoupons();
        Coupon couponToDelete = optCoupon.get();
        if (!couponToDelete.getCompany().equals(company)) {
            throw new UnauthorizedOperationException(ExceptionMessage.UNAUTHORIZED);
        }
        for (Coupon currentCoupon : allCompanyCoupons) {
            if (currentCoupon.getUuid().equals(couponUuid)) {
                couponRepository.deleteByUuid(couponUuid);
            }
        }
    }

    @Override
    @Transactional
    public Optional<CouponDto> updateAmount(UUID companyUuid, UUID couponUuid, Integer amount) {
        Optional<Company> optCompany = companyRepository.findByUuid(companyUuid);
        Optional<Coupon> optCoupon = couponRepository.findByUuid(couponUuid);
        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY);
        }
        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException(ExceptionMessage.NO_SUCH_COUPON);
        }
        if (amount == null || amount < MIN_VALUE) {
            throw new InvalidAmountException(ExceptionMessage.INVALID_AMOUNT);
        }

        Coupon coupon = optCoupon.get();
        if (!optCompany.get().equals(coupon.getCompany())) {
            throw new UnauthorizedOperationException(ExceptionMessage.UNAUTHORIZED);
        }
        coupon.setAmount(amount);
        return Optional.of(couponMapper.map(couponRepository.save(coupon)));
    }

    @Override
    public List<CouponDto> getAllCompanyCoupons(UUID companyUuid) {
        Optional<Company> optCompany = companyRepository.findByUuid(companyUuid);
        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY);
        }
        return couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCompany().equals(optCompany.get()))
                .map(couponMapper::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<CompanyDto> updateDetails(UUID companyUuid, String email, String password) {
        Optional<Company> optCompany = companyRepository.findByUuid(companyUuid);
        if (optCompany.isEmpty()) {
            throw new NoSuchCompanyException(ExceptionMessage.NO_SUCH_COMPANY);
        }
        check.emailValidation(email);
        check.passwordValidation(password);
        Company company = optCompany.get();
        company.setEmail(email);
        company.setPassword(password);
        return Optional.of(companyMapper.map(companyRepository.save(company)));
    }

}

