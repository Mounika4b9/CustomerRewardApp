package com.retailer.customerRewardApp.dto;

import java.util.List;

import com.retailer.customerRewardApp.entity.Customer;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RewardResponse {
	private Customer customer;
	private List<TransactionDto> transaction;
	private int totalRewardPoints;
	
	
	
	

}
