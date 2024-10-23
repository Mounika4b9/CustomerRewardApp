package com.retailer.customerRewardApp.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.retailer.customerRewardApp.controller.CustomerController;
import com.retailer.customerRewardApp.dto.CustomerDto;
import com.retailer.customerRewardApp.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@Mock
	private CustomerServiceImpl service;

	@InjectMocks
	private CustomerController controller;

	private CustomerDto invalidCustomerDto;
	private CustomerDto validCustomerDto;

	@BeforeEach
	public void setUp() {
		// MockitoAnnotations.initMocks(this); // Initialize mocks before each test

		validCustomerDto = new CustomerDto();
		validCustomerDto.setName("John Doe");
		validCustomerDto.setPhoneNo("1234567890");

		invalidCustomerDto = new CustomerDto();
		invalidCustomerDto.setName(null);
		invalidCustomerDto.setPhoneNo(null);
	}

	@Test
	public void testSaveCustomer_Success() {
		when(service.saveCustomer(validCustomerDto)).thenReturn(null); // Adjust the return value accordingly

		ResponseEntity<String> response = controller.saveCustomer(validCustomerDto);
		assertEquals("Customer Details are saved Successfully", response.getBody());
	}

	@Test
	public void testSaveCustomer_InvalidRequest() {
		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			controller.saveCustomer(invalidCustomerDto);
		});
		assertEquals("Invalid Request", thrown.getMessage());
	}

}
