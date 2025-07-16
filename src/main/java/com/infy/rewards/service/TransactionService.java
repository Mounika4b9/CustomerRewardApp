package com.infy.rewards.service;

import java.util.List;

import com.infy.rewards.dto.TransactionDto;

public interface TransactionService {

	public int calculatePoints(Double txnAmount);

	List<TransactionDto> getAllTransactions(long customerId);

	TransactionDto saveTransaction(long customerId, TransactionDto transactionDto);

}
