package com.infy.rewards.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.rewards.Exception.CustomerNotFound;
import com.infy.rewards.Exception.InvalidInputException;
import com.infy.rewards.dto.TransactionDto;
import com.infy.rewards.entity.Customer;
import com.infy.rewards.entity.Transaction;
import com.infy.rewards.repository.CustomerRepository;
import com.infy.rewards.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionReposiotory;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	// Creating transaction data in DB

	@Override
	public TransactionDto saveTransaction(long customerId, TransactionDto transactionDto) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFound(String.format("customer ID %d not found", customerId)));
		Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
		transaction.setCustomer(customer);
		int points = calculatePoints(transaction.getAmount());
		transaction.setRewardPoints(points);
		Transaction savedTransaction = transactionReposiotory.save(transaction);
		TransactionDto dto = modelMapper.map(savedTransaction, TransactionDto.class);
		return dto;
	}

	/*
	 * Get All Transaction details By CustomerID
	 */
	@Override
	public List<TransactionDto> getAllTransactions(long customerId) {
		customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFound(String.format("Customer ID %d not found", customerId)));
		List<Transaction> txns = transactionReposiotory.findAllByCustomerId(customerId);

		return txns.stream().map(txn -> modelMapper.map(txn, TransactionDto.class)).collect(Collectors.toList());
	}

	/*
	 * calculate the reward points based on purchase amount of each transaction
	 */

	@Override
	public int calculatePoints(Double amount) {
		if (amount == null || amount < 0) {
			logger.error("invalid transaction amount:{}", amount);
			throw new InvalidInputException("Transaction amount must be a positive number");
		}
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100.0;
		}
		if (amount > 50) {
			points += (amount - 50);
		}
		logger.debug("calculated points:{}", points);
		return points;
	}
}
