package com.retailer.customerRewardApp.service;

import java.time.LocalDate;
import java.util.Map;
import com.retailer.customerRewardApp.dto.RewardResponse;

public interface RewardService {

	RewardResponse getTotalRewardsAndTransactionDetailsByCustId(Long customerId, int months);
	Map<String, Object> getMontlyTransactionAndDetails(Long customerId, LocalDate startDate, LocalDate endDate);
	
	Map<String, Integer> getMontlyRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate);
	
	

}
