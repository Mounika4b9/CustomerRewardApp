package com.retailer.customerRewardApp.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.retailer.customerRewardApp.controller.RewardController;
import com.retailer.customerRewardApp.dto.RewardResponse;
import com.retailer.customerRewardApp.service.RewardService;
@ExtendWith(MockitoExtension.class)
public class RewardControllerTest {
	

	    @Mock
	    private RewardService service;

	    @InjectMocks
	    private RewardController controller;

	    private Long customerId;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private int months;

	    @BeforeEach
	    public void setUp() {
	        customerId = 1L;
	        startDate = LocalDate.of(2023, 1, 1);
	        endDate = LocalDate.of(2023, 12, 31);
	        months = 3;
	    }

	    @Test
	    public void testGetRewards_Success() {
	        Map<String, Integer> rewards = new HashMap<>();
	        rewards.put("January", 100);
	        rewards.put("February", 150);

	        when(service.getMontlyRewardPoints(customerId, startDate, endDate)).thenReturn(rewards);

	        ResponseEntity<Map<String, Integer>> response = controller.getRewards(customerId, startDate, endDate);
	        assertEquals(rewards, response.getBody());
	        assertEquals(ResponseEntity.ok(rewards), response);
	    }

	    @Test
	    public void testGetTotalRewardsAndTransactionDetailsByCustId_Success() {
	        RewardResponse rewardResponse = new RewardResponse();
	        // Populate rewardResponse with test data

	        when(service.getTotalRewardsAndTransactionDetailsByCustId(customerId, months)).thenReturn(rewardResponse);

	        ResponseEntity<RewardResponse> response = controller.getTotalRewardsAndTransactionDetailsByCustId(customerId, months);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(rewardResponse, response.getBody());
	    }

	    @Test
	    public void testGetMontlyTransactionAndDetails_Success() {
	        Map<String, Object> transactionDetails = new HashMap<>();
	        // Populate transactionDetails with test data

	        when(service.getMontlyTransactionAndDetails(customerId, startDate, endDate)).thenReturn(transactionDetails);

	        ResponseEntity<Map<String, Object>> response = controller.getMontlyTransactionAndDetails(customerId, startDate, endDate);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(transactionDetails, response.getBody());
	    }


}
