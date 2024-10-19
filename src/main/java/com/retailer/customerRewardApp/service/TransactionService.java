package com.retailer.customerRewardApp.service;

import java.util.List;

import com.retailer.customerRewardApp.dto.TransactionDto;

public interface TransactionService {

	public int calculatePoints(Double txnAmount);

	List<TransactionDto> getAllTransactions(long customerId);

	TransactionDto saveTransaction(long customerId, TransactionDto transactionDto);

}
