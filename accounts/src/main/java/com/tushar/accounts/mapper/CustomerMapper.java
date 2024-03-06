package com.tushar.accounts.mapper;

import com.tushar.accounts.dto.CustomerDto;
import com.tushar.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomerEntity(Customer customer, CustomerDto customerDto) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
}
