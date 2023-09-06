package com.lihicouponsystem.service.customer;


import com.lihicouponsystem.entities.Company;
import com.lihicouponsystem.entities.Coupon;
import com.lihicouponsystem.entities.Customer;
import com.lihicouponsystem.mapper.coupon.CouponMapper;
import com.lihicouponsystem.mapper.customer.CustomerMapper;
import com.lihicouponsystem.repositories.CouponRepository;
import com.lihicouponsystem.repositories.CustomerRepository;
import com.lihicouponsystem.service.ValidationCheck;
import com.lihicouponsystem.service.exceptions.*;
import com.lihicouponsystem.web.dto.CompanyDto;
import com.lihicouponsystem.web.dto.CouponDto;
import com.lihicouponsystem.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final CustomerMapper customerMapper;
    private final CouponMapper couponMapper;
    private static final Integer MIN_VALUE = 1;
    private static final Long WEEK_IN_MILLIS = TimeUnit.DAYS.toMillis(7);
    private final ValidationCheck check;

    @Override
    public Optional<CustomerDto> getCustomerByUuid(UUID customerUuid) {
        return customerRepository.findByUuid(customerUuid).map(customerMapper::map);
    }

    @Override
    public Optional<CouponDto> purchaseCoupon(UUID customerUuid, UUID couponUuid) {
        Optional<Coupon> optCoupon = couponRepository.findByUuid(couponUuid);
        Optional<Customer> optCustomer = customerRepository.findByUuid(customerUuid);
        if (optCoupon.isEmpty()) {
            throw new NoSuchCouponException(ExceptionMessage.NO_SUCH_COUPON);
        }
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(ExceptionMessage.NO_SUCH_CUSTOMER);
        }
        Coupon couponToPurchase = optCoupon.get();
        Customer thisCustomer = optCustomer.get();

        if (couponToPurchase.getAmount() == null || couponToPurchase.getAmount() < MIN_VALUE) {
            throw new InvalidAmountException(ExceptionMessage.INVALID_AMOUNT_TO_PURCHASE);
        }

        List<Coupon> allCustomerCoupons = thisCustomer.getCoupons();
        for (Coupon currentCoupon : allCustomerCoupons) {
            if (currentCoupon.getUuid().equals(couponToPurchase.getUuid())) {
                throw new InvalidInsertionException(ExceptionMessage.DOUBLE_PURCHASE);
            }
        }
        thisCustomer.addCoupon(couponToPurchase);
        couponToPurchase.setAmount(couponToPurchase.getAmount() - 1);
        return Optional.of(couponMapper.map(couponRepository.save(couponToPurchase)));
    }

    @Override
    public List<CouponDto> getAllCustomerCoupons(UUID customerUuid) {
        Optional<Customer> optCustomer = customerRepository.findByUuid(customerUuid);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(ExceptionMessage.NO_SUCH_CUSTOMER);
        }
        return optCustomer.get().getCoupons().stream().map(couponMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponDto> getAllCustomerNotPurchasedCoupons(UUID customerUuid) {
        Optional<Customer> optCustomer = customerRepository.findByUuid(customerUuid);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(ExceptionMessage.NO_SUCH_CUSTOMER);
        }
        List<Coupon> allCustomerCoupons = optCustomer.get().getCoupons().stream().toList();
        List<Coupon> allCoupons = couponRepository.findAll();
        if (allCoupons.removeAll(allCustomerCoupons)) {
            return allCoupons.stream().map(couponMapper::map).collect(Collectors.toList());
        } else {
            throw new NoSuchCouponException(ExceptionMessage.NO_SUCH_COUPON);
        }
    }

    @Override
    public List<CouponDto> getNearlyExpiredCustomerCoupons(UUID customerUuid) {
        Optional<Customer> optCustomer = customerRepository.findByUuid(customerUuid);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(ExceptionMessage.NO_SUCH_CUSTOMER);
        }
        Date oneWeekFromNow = new Date(System.currentTimeMillis() + WEEK_IN_MILLIS);
        List<Coupon> allCustomerCoupons = optCustomer.get().getCoupons().stream().toList();
        List<Coupon> nearlyExpiredCustomerCoupons = allCustomerCoupons.stream()
                .filter(coupon -> coupon.getEndDate().before(oneWeekFromNow))
                .collect(Collectors.toList());
        return nearlyExpiredCustomerCoupons.stream().map(couponMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<CustomerDto> updateDetails(UUID customerUuid, String email, String password) {
        Optional<Customer> optCustomer = customerRepository.findByUuid(customerUuid);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(ExceptionMessage.NO_SUCH_CUSTOMER);
        }
        check.emailValidation(email);
        check.passwordValidation(password);
        Customer customer = optCustomer.get();
        customer.setEmail(email);
        customer.setPassword(password);
        return Optional.of(customerMapper.map(customerRepository.save(customer)));
    }

}
