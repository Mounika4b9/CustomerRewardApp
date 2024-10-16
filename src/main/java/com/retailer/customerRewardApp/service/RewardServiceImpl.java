package com.retailer.customerRewardApp.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailer.customerRewardApp.Exception.CustomerNotFound;
import com.retailer.customerRewardApp.dto.RewardResponse;
import com.retailer.customerRewardApp.dto.TransactionDto;
import com.retailer.customerRewardApp.entity.Customer;
import com.retailer.customerRewardApp.entity.Transaction;
import com.retailer.customerRewardApp.repository.CustomerRepository;
import com.retailer.customerRewardApp.repository.TransactionRepository;

@Service
public class RewardServiceImpl implements RewardService {
	
@Autowired
private TransactionRepository transactionRepository;
@Autowired
private CustomerRepository customerRepository;
@Autowired
private TransactionService service;

@Autowired
private ModelMapper modelMapper;
private static final Logger logger=LoggerFactory.getLogger(RewardServiceImpl.class);

/*Calculating the monthly wise reward points and retrieve  transaction details
 * and customer details 
   based on customer id*/

	@Override
	public Map<String,Object> getMontlyTransactionAndDetails(Long customerId,LocalDate startDate, LocalDate endDate) {
		Customer customer=customerRepository.findById(customerId).orElseThrow(
				()-> new CustomerNotFound(String.format("Customer ID %d not found", customerId)));
		logger.info("fetching customer details for cuustomerId:{}",customerId);
		List<Transaction> transactions =transactionRepository.findByCustomerIdAndDateBetween(customerId,startDate,endDate);
		List<TransactionDto> transactionDto=transactions.stream().map(
				txn -> modelMapper.map(txn, TransactionDto.class)).collect(Collectors.toList());
		Map<String,Map<String,Object>> monthlyData=new HashMap<>();
		for(TransactionDto transaction:transactionDto) {
			String month=transaction.getTransactionDate().getMonth().toString();
			monthlyData.putIfAbsent(month,new HashMap<>());
			Map<String,Object> monthData=monthlyData.get(month);
			List<TransactionDto> monthTransactions=(List<TransactionDto>) monthData.getOrDefault("transactions", new ArrayList<>());
			monthTransactions.add(transaction);
			monthData.put("transactions",monthTransactions);
			int points=transaction.getRewardPoints();
			int monthlyPoints=(int)monthData.getOrDefault("points", 0)+points;
			monthData.put("points", monthlyPoints);
		}
		Map<String,Object> response=new HashMap<>();
		response.put("customer", customer);
		response.put("monthlyData", monthlyData);
		logger.info("fecthing total reward points:{}",customerId);
		return response;
	
		}
	
	/*Calculating total reward points and retrieve  transaction details
	 * and customer details 
	   based on customer id*/
	public RewardResponse getTotalRewardsAndTransactionDetailsByCustId(Long customerId, int months) {
		Customer customer=customerRepository.findById(customerId).orElseThrow(
				()-> new CustomerNotFound(String.format("Customer ID %d not found", customerId)));
		logger.info("Fetching customer details for customer ID: {}",customerId);
		int totalPoints=0;
		LocalDate endDate=LocalDate.now();
		LocalDate startDate=LocalDate.now().minusMonths(months);
		Map<String,Integer> monthlyPoints=getMontlyRewardPoints(customerId,startDate,endDate);
		for(Map.Entry<String,Integer>entry:monthlyPoints.entrySet()) {
			totalPoints+=entry.getValue();
			
		}
		
	List<TransactionDto> transactions=service.getAllTransactions(customerId);
		RewardResponse response=new RewardResponse();
		response.setCustomer(customer);
		response.setTransaction(transactions);
		response.setTotalRewardPoints(totalPoints);
		
		logger.info("Total  reward points for customerId:{}",customerId);
		return response;
		
	}
/*month and monthPoints 
 * between start and end date
 *  by customerID*/
	
	@Override
	public Map<String,Integer> getMontlyRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate) {
		logger.info("fetching transaction details by customerId:{}",customerId);
		List<Transaction> transactions=transactionRepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate);
		Map<String,Integer> monthlyPoints=new HashMap<>();
		for(Transaction transaction:transactions) {
			String month=transaction.getDate().getMonth().toString();
			int points=transaction.getRewardPoints();
			monthlyPoints.put(month,monthlyPoints.getOrDefault(month,0)+points);
			
		}
		return monthlyPoints;
		
	}
	
	

}
