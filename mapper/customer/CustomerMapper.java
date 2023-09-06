package com.lihicouponsystem.mapper.customer;

import com.lihicouponsystem.entities.Customer;
import com.lihicouponsystem.web.dto.CustomerDto;

public interface CustomerMapper {
    Customer map(CustomerDto customerDto);

    CustomerDto map(Customer customer);
}
