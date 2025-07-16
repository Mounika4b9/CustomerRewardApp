package com.infy.rewards.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewards.dto.CustomerDto;
import com.infy.rewards.service.CustomerService;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@PostMapping("/save")
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerDto customerDto) {
		if (customerDto == null || customerDto.getName() == null || customerDto.getPhoneNo() == null
				|| customerDto.getPhoneNo() == "")
			throw new RuntimeException("Invalid Request");

		customerService.saveCustomer(customerDto);
		return ResponseEntity.ok("Customer Details are saved Successfully");

	}

}
