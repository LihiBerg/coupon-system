package com.lihicouponsystem.mapper.customer;

import com.lihicouponsystem.entities.Customer;
import com.lihicouponsystem.mapper.coupon.CouponMapper;
import com.lihicouponsystem.web.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerMapperImpl implements CustomerMapper {
    private final CouponMapper couponMapper;

    @Autowired
    public CustomerMapperImpl(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    @Override
    public Customer map(CustomerDto customerDto) {
        if (customerDto == null) {
            return null;
        }
        return Customer.builder().uuid(customerDto.getUuid()).firstName(customerDto.getFirst_name())
                .lastName(customerDto.getLast_name()).email(customerDto.getEmail())
                .password(customerDto.getPassword()).build();
    }

    @Override
    public CustomerDto map(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerDto.builder().uuid(customer.getUuid()).first_name(customer.getFirstName())
                .last_name(customer.getLastName()).email(customer.getEmail())
                .password(customer.getPassword()).build();
    }
}


