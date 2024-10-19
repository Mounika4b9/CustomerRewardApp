package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.modelmapper.ModelMapper;

import com.retailer.customerRewardApp.Exception.CustomerNotFound;
import com.retailer.customerRewardApp.dto.RewardResponse;
import com.retailer.customerRewardApp.dto.TransactionDto;
import com.retailer.customerRewardApp.entity.Customer;
import com.retailer.customerRewardApp.entity.Transaction;
import com.retailer.customerRewardApp.repository.CustomerRepository;
import com.retailer.customerRewardApp.repository.TransactionRepository;
import com.retailer.customerRewardApp.service.RewardService;
import com.retailer.customerRewardApp.service.RewardServiceImpl;
import com.retailer.customerRewardApp.service.TransactionService;
import com.retailer.customerRewardApp.service.TransactionServiceImpl;

public class RewardServiceTest {

	@InjectMocks
	RewardServiceImpl rewardService;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	TransactionServiceImpl transactionService;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	ModelMapper modelMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Test Lest Than 50 amount transaction")
	public void testCalculatePoints_LessThan50() {
		Double amount = 30d;
		int expectedValue = 0;
		int actualValue = transactionService.calculatePoints(amount);
		assertEquals(expectedValue, actualValue);

	}

	@Test
	@DisplayName("Test amount 120 transaction")
	public void testCalculatePointsMoreThan100() {
		Double amount = 120d;
		int expectedValue = 90;
		int actualValue = transactionService.calculatePoints(amount);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	@DisplayName("Test get all transactions")

	// Assert
	public void testGetAllTransactions_Success() {
		// Arrange
		long customerId = 7;
		Double amount = 120d;
		Customer customer = new Customer();
		customer.setId(customerId);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDate(LocalDate.now());
		transaction.setRewardPoints(transactionService.calculatePoints(amount));
		transaction.setId(13);
		TransactionDto transactionDto = new TransactionDto();
		List<Transaction> transactions = Collections.singletonList(transaction);
		List<TransactionDto> transactionDtos = Collections.singletonList(transactionDto);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(transactionRepository.findAllByCustomerId(customerId)).thenReturn(transactions);
		when(modelMapper.map(transaction, TransactionDto.class)).thenReturn(transactionDto);

		// Act
		List<TransactionDto> result = transactionService.getAllTransactions(customerId);

		// Assert
		assertEquals(transactionDtos, result);
		verify(customerRepository).findById(customerId);
		verify(transactionRepository).findAllByCustomerId(customerId);
		verify(modelMapper).map(transaction, TransactionDto.class);
	}

	@Test
	public void testGetTotalRewardsAndTransactionDetailsByCustId() {
		// Arrange
		Long customerId = 1L;
		int months = 3;

		Customer customer = new Customer();
		customer.setId(customerId);

		Transaction transaction = new Transaction();
		transaction.setRewardPoints(10);
		List<Transaction> transactions = Arrays.asList(transaction);

		TransactionDto transactionDto = new TransactionDto();
		List<TransactionDto> transactionDtos = Arrays.asList(transactionDto);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(transactionRepository.findByCustomerIdAndDateBetween(eq(customerId), any(LocalDate.class),
				any(LocalDate.class))).thenReturn(transactions);
		when(modelMapper.map(transaction, TransactionDto.class)).thenReturn(transactionDto);

		// Act
		RewardResponse response = rewardService.getTotalRewardsAndTransactionDetailsByCustId(customerId, months);

		// Assert
		assertEquals(customer, response.getCustomer());
		assertEquals(transactionDtos, response.getTransaction());
		assertEquals(10, response.getTotalRewardPoints());

		verify(customerRepository).findById(customerId);
		verify(transactionRepository).findByCustomerIdAndDateBetween(eq(customerId), any(LocalDate.class),
				any(LocalDate.class));
		verify(modelMapper).map(transaction, TransactionDto.class);
	}

	@Test
	public void testGetTotalRewardsAndTransactionDetailsByCustId_CustomerNotFound() {
		// Arrange
		Long customerId = 101L;

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		RuntimeException thrown =

				assertThrows(RuntimeException.class, () -> {
					rewardService.getTotalRewardsAndTransactionDetailsByCustId(customerId, anyInt());
				});

		// Verify the exception message
		assertEquals("Customer ID 101 not found", thrown.getMessage());
	}

}
