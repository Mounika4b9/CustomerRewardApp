package com.retailer.customerRewardApp.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.rewards.controller.CustomerController;
import com.infy.rewards.dto.CustomerDto;
import com.infy.rewards.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Autowired
	private ObjectMapper objectMapper;

	private CustomerDto validCustomerDto;
	private CustomerDto invalidCustomerDto;

	@BeforeEach
	public void setUp() {
		validCustomerDto = new CustomerDto();
		validCustomerDto.setName("bartosz");
		validCustomerDto.setPhoneNo("1234567890");

		invalidCustomerDto = new CustomerDto();
		invalidCustomerDto.setName(null);
		invalidCustomerDto.setPhoneNo(null);
	}

	@Test
	public void testSaveCustomer_Success() throws Exception {
		mockMvc.perform(post("/api/customer/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validCustomerDto))).andExpect(status().isOk())
				.andExpect(content().string("Customer Details are saved Successfully"));
	}

	@Test
	public void testSaveCustomer_BadRequest() throws Exception {
		mockMvc.perform(post("/api/customer/save").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidCustomerDto))).andExpect(status().isBadRequest());

	}

}
