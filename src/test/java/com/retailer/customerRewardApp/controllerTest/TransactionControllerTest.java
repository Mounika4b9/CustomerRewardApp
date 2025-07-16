package com.retailer.customerRewardApp.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.rewards.controller.TransactionController;
import com.infy.rewards.dto.TransactionDto;
import com.infy.rewards.service.TransactionService;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@Autowired
	private ObjectMapper objectMapper;

	private TransactionDto validTransactionDto;
	private TransactionDto invalidTransactionDto;

	@BeforeEach
	public void setUp() {
		validTransactionDto = new TransactionDto();
		validTransactionDto.setAmount(300);
		validTransactionDto.setTransactionDate(LocalDate.now());

		invalidTransactionDto = new TransactionDto();
		invalidTransactionDto.setAmount(0); // Invalid amount
		invalidTransactionDto.setTransactionDate(null); // Invalid date
	}

	@Test
	public void testSaveTransaction_Success() throws Exception {
		mockMvc.perform(post("/api/{customerid}/transaction", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validTransactionDto))).andExpect(status().isOk())
				.andExpect(content().string("Transaction Details are saved successfully"));
	}

	@Test
	public void testSaveTransaction_InvalidDetails() throws Exception {
		mockMvc.perform(post("/api/{customerid}/transaction", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidTransactionDto))).andExpect(status().isBadRequest())
				.andExpect(content().string("Invalid Transaction Details"));
	}

	@Test
	public void testGetAllTransactions_Success() throws Exception {
		List<TransactionDto> transactionList = new ArrayList<>();
		transactionList.add(validTransactionDto); // Add a valid transaction for testing

		when(transactionService.getAllTransactions(1L)).thenReturn(transactionList);

		mockMvc.perform(get("/api/{customerId}/txns", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].amount").value(300));
	}

	@Test
	public void testGetAllTransactions_NotFound() throws Exception {
		when(transactionService.getAllTransactions(999L)).thenReturn(new ArrayList<>());

		mockMvc.perform(get("/api/{customerId}/txns", 999).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string("No transactions found for customer ID: 999"));
	}
}
