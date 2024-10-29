package com.retailer.customerRewardApp.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.retailer.customerRewardApp.controller.RewardController;
import com.retailer.customerRewardApp.dto.RewardResponse;
import com.retailer.customerRewardApp.service.RewardService;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RewardService service;

	private Long customerId = 1L;
	private LocalDate startDate;
	private LocalDate endDate;

	@BeforeEach
	public void setUp() {
		startDate = LocalDate.of(2024, 1, 1);
		endDate = LocalDate.of(2024, 1, 31);
	}

	@Test
	public void testGetRewards_Success() throws Exception {
		Map<String, Integer> rewards = new HashMap<>();
		rewards.put("January", 100);
		when(service.getMontlyRewardPoints(customerId, startDate, endDate)).thenReturn(rewards);

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", startDate.toString())
				.param("endDate", endDate.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.January").value(100));
	}

	@Test
	public void testGetRewards_NotFound() throws Exception {
		when(service.getMontlyRewardPoints(customerId, startDate, endDate)).thenReturn(new HashMap<>());

		mockMvc.perform(get("/rewards/{customerId}", customerId).param("startDate", startDate.toString())
				.param("endDate", endDate.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string("No rewards found for customer ID: " + customerId));
	}

	@Test
	public void testGetTotalRewardsAndTransactionDetails_Success() throws Exception {
		RewardResponse rewardResponse = new RewardResponse();
		// Populate rewardResponse with test data as needed
		when(service.getTotalRewardsAndTransactionDetailsByCustId(customerId, 3)).thenReturn(rewardResponse);

		mockMvc.perform(get("/rewards/{customerId}/rewardDetails", customerId).param("months", "3")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testGetTotalRewardsAndTransactionDetails_NotFound() throws Exception {
		when(service.getTotalRewardsAndTransactionDetailsByCustId(customerId, 3)).thenReturn(null);

		mockMvc.perform(get("/rewards/{customerId}/rewardDetails", customerId).param("months", "3")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(content().string("No reward details found for customer ID: " + customerId));
	}

	@Test
	public void testGetMontlyTransactionAndDetails_Success() throws Exception {
		Map<String, Object> transactionDetails = new HashMap<>();
		transactionDetails.put("transactionId", 1);

		when(service.getMontlyTransactionAndDetails(customerId, startDate, endDate)).thenReturn(transactionDetails);

		mockMvc.perform(get("/rewards/monthlyPoints/{customerId}", customerId).param("startDate", startDate.toString())
				.param("endDate", endDate.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.transactionId").value(1));
	}

	@Test
	public void testGetMontlyTransactionAndDetails_NotFound() throws Exception {
		when(service.getMontlyTransactionAndDetails(customerId, startDate, endDate)).thenReturn(new HashMap<>());

		mockMvc.perform(get("/rewards/monthlyPoints/{customerId}", customerId).param("startDate", startDate.toString())
				.param("endDate", endDate.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string("No monthly transaction details found for customer ID: " + customerId));
	}
}
