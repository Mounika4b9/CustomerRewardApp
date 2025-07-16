package com.infy.rewards.service;

import java.time.LocalDate;
import java.util.Map;

import com.infy.rewards.dto.RewardResponse;

public interface RewardService {

	RewardResponse getTotalRewardsAndTransactionDetailsByCustId(Long customerId, int months);

	Map<String, Object> getMontlyTransactionAndDetails(Long customerId, LocalDate startDate, LocalDate endDate);

	Map<String, Integer> getMontlyRewardPoints(Long customerId, LocalDate startDate, LocalDate endDate);

}
