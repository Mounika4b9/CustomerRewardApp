package com.infy.rewards.controller;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.rewards.Exception.ResourceNotFoundException;
import com.infy.rewards.dto.RewardResponse;
import com.infy.rewards.service.RewardService;

@RestController
@RequestMapping("/rewards")
public class RewardController {
	@Autowired
	private RewardService service;
	private static final Logger logger = LoggerFactory.getLogger(RewardController.class);

	@GetMapping("/{customerId}")
	public ResponseEntity<Map<String, Integer>> getRewards(@PathVariable Long customerId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		logger.info("Recieved request to get customer details for customer ID:{}", customerId);
		Map<String, Integer> rewards = service.getMontlyRewardPoints(customerId, startDate, endDate);
		if (rewards.isEmpty()) {
			throw new ResourceNotFoundException("No rewards found for customer ID: " + customerId);
		}
		return ResponseEntity.ok(rewards);
	}

	@GetMapping("/{customerId}/rewardDetails")
	public ResponseEntity<RewardResponse> getTotalRewardsAndTransactionDetailsByCustId(@PathVariable Long customerId,
			@RequestParam(value = "months", defaultValue = "3") int months) {
		logger.info("Recieved the request for getting  total reward points:{}", customerId);

		RewardResponse rewardResponse = service.getTotalRewardsAndTransactionDetailsByCustId(customerId, months);
		if (rewardResponse == null) {
			throw new ResourceNotFoundException("No reward details found for customer ID: " + customerId);
		}
		return ResponseEntity.ok(rewardResponse);

	}

	@GetMapping("/monthlyPoints/{customerId}")
	public ResponseEntity<Map<String, Object>> getMontlyTransactionAndDetails(@PathVariable Long customerId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		logger.info("Recieved request to get monthly transaction details for customerId:{}", customerId);
		{
			Map<String, Object> transactionDetails = service.getMontlyTransactionAndDetails(customerId, startDate,
					endDate);

			if (transactionDetails.isEmpty()) {
				throw new ResourceNotFoundException(
						"No monthly transaction details found for customer ID: " + customerId);
			}
			return ResponseEntity.ok(transactionDetails);
		}

	}
}