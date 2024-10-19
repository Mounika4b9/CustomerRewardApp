package com.retailer.customerRewardApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.customerRewardApp.dto.CustomerDto;
import com.retailer.customerRewardApp.entity.Customer;
import com.retailer.customerRewardApp.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	/*
	 * saving CustomerDetails in DB
	 */

	public CustomerDto saveCustomer(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDto dto = modelMapper.map(savedCustomer, CustomerDto.class);
		return dto;
	}

}
