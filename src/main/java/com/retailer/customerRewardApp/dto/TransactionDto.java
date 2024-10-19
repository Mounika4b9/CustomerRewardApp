package com.retailer.customerRewardApp.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDto {
	private long transactionId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate transactionDate;
	private double amount;
	private int rewardPoints;

}
