package com.infy.rewards.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewards.dto.CustomerDto;
import com.infy.rewards.entity.Customer;
import com.infy.rewards.repository.CustomerRepository;

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
