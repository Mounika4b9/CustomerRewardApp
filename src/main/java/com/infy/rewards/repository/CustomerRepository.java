package com.infy.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.rewards.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
