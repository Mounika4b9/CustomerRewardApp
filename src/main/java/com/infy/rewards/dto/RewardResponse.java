package com.infy.rewards.dto;

import java.util.List;

import com.infy.rewards.entity.Customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardResponse {
	private Customer customer;
	private List<TransactionDto> transaction;
	private int totalRewardPoints;

}
