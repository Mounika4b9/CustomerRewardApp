package com.retailer.customerRewardApp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retailer.customerRewardApp.entity.Transaction;


@Repository
public interface TransactionRepository extends  JpaRepository<Transaction,Long>{
	
	List<Transaction> findByCustomerIdAndDateBetween(Long customerId,LocalDate startDate,LocalDate endDate);

	List<Transaction> findAllByCustomerId(long customerId);


	
	

}
