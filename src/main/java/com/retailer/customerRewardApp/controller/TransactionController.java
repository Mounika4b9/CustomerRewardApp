package com.retailer.customerRewardApp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retailer.customerRewardApp.Exception.ResourceNotFoundException;
import com.retailer.customerRewardApp.dto.TransactionDto;
import com.retailer.customerRewardApp.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@PostMapping("/{customerid}/transaction")
	public ResponseEntity<String> saveTransaction(@PathVariable(name = "customerid") long customerid,
			@RequestBody TransactionDto transactionDto) {

		if (transactionDto == null || transactionDto.getAmount() <= 0 || transactionDto.getTransactionDate() == null) {
			throw new RuntimeException("Invalid Transaction Details");
		}

		transactionService.saveTransaction(customerid, transactionDto);
		return ResponseEntity.ok("Transaction Details are saved successfully");

	}

	@GetMapping("/{customerId}/txns")
	public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable(name = "customerId") long customerId) {
		List<TransactionDto> transactions = transactionService.getAllTransactions(customerId);
		if (transactions == null || transactions.isEmpty()) {
			throw new ResourceNotFoundException("No transactions found for customer ID: " + customerId);
		}

		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

}
