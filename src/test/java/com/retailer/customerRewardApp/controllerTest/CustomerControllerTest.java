package com.retailer.customerRewardApp.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.customerRewardApp.controller.CustomerController;
import com.retailer.customerRewardApp.dto.CustomerDto;
import com.retailer.customerRewardApp.service.CustomerService;
import com.retailer.customerRewardApp.service.CustomerServiceImpl;

public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CustomerServiceImpl service;
	@InjectMocks
	private CustomerController controller;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveCustomer_Success() throws Exception {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId(1);
		customerDto.setName("John Doe");
		customerDto.setPhoneNo("1234567890");

		doNothing().when(service).saveCustomer(any(CustomerDto.class));

		mockMvc.perform(post("/save") // Ensure the URL matches your controller
				.content(asJsonString(customerDto)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) // Change to isCreated() if that's what
																				// your controller returns
				.andExpect(content().string("Customer Details are saved Successfully"));

		verify(service, times(2)).saveCustomer(any(CustomerDto.class));
		// Assuming saveCustomer returns CustomerDto
		// when(service.saveCustomer(any(CustomerDto.class))).thenReturn(customerDto);

	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
	}
}
