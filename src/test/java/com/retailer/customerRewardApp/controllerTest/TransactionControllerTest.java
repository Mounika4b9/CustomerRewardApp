package com.retailer.customerRewardApp.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retailer.customerRewardApp.controller.TransactionController;
import com.retailer.customerRewardApp.dto.TransactionDto;
import com.retailer.customerRewardApp.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionController transactionController;

	private TransactionDto transactionDto;
	private long customerId;

	@BeforeEach
	public void setUp() {
		transactionDto = new TransactionDto();
		// Populate transactionDto with test data

		customerId = 1L;
	}

	@Test
	public void testSaveTransaction_Success() {
		// Use doNothing().when for void methods
		doAnswer(invocation -> {
			return null;
		}).when(transactionService).saveTransaction(customerId, transactionDto);

		ResponseEntity<String> response = transactionController.saveTransaction(customerId, transactionDto);
		assertEquals("Transaction Details are saved successfully", response.getBody());
	}

	@Test
	public void testGetAllTransactions_Success() {
		List<TransactionDto> transactionList = new ArrayList<>();
		// Populate transactionList with test data

		when(transactionService.getAllTransactions(customerId)).thenReturn(transactionList);

		ResponseEntity<List<TransactionDto>> response = transactionController.getAllTransactions(customerId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(transactionList, response.getBody());
	}
}
