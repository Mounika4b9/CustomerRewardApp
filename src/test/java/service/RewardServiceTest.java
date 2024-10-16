package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;


import com.retailer.customerRewardApp.dto.RewardResponse;
import com.retailer.customerRewardApp.dto.TransactionDto;
import com.retailer.customerRewardApp.entity.Customer;
import com.retailer.customerRewardApp.entity.Transaction;
import com.retailer.customerRewardApp.repository.CustomerRepository;
import com.retailer.customerRewardApp.repository.TransactionRepository;
import com.retailer.customerRewardApp.service.RewardService;
import com.retailer.customerRewardApp.service.TransactionService;

public class RewardServiceTest {
	@InjectMocks
	private RewardService rewardService;
	@Mock
	private TransactionRepository transactionrepository;
	@Mock
	private TransactionService tranactionService;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private ModelMapper modelMapper;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	@DisplayName("Test Lest Than 50 amount transaction")
	public void testCalculatePoints_LessThan50() {
		double amount=30;
		int expectedValue=0;
		int actualValue=tranactionService.calculatePoints(amount);
		assertEquals(expectedValue,actualValue);
		
	}
	public void testCalculatePointsMoreThan100() {
		double amount=120;
		int expectedValue=90;
		int actualValue=tranactionService.calculatePoints(amount);
		assertEquals(expectedValue,actualValue);
	}
	
	@Test
    public void testGetTotalRewardsAndTransactionDetailsByCustId() {
        Long customerId = 1L;
        int months = 3;

        Customer customer = new Customer();
        customer.setId(customerId);

        List<TransactionDto> transactions = new ArrayList<>();
        TransactionDto transaction = new TransactionDto();
        transactions.add(transaction);

        Map<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put("2024-08", 10);
        monthlyPoints.put("2024-09", 20);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(tranactionService.getAllTransactions(customerId)).thenReturn(transactions);
        when(rewardService.getMontlyRewardPoints(customerId, LocalDate.now().minusMonths(months), LocalDate.now())).thenReturn(monthlyPoints);

        RewardResponse response = rewardService.getTotalRewardsAndTransactionDetailsByCustId(customerId, months);

        assertEquals(customer, response.getCustomer());
        assertEquals(transactions, response.getTransaction());
        assertEquals(30, response.getTotalRewardPoints());
        verify(customerRepository).findById(customerId);
        verify(tranactionService).getAllTransactions(customerId);
	
	}
	@Test
    public void testGetMontlyTransactionAndDetails() {
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        Customer customer = new Customer();
        customer.setId(customerId);

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDate.of(2023, 5, 10));
        transaction.setRewardPoints(10);
        transactions.add(transaction);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionDate(LocalDate.of(2023, 5, 10));
        transactionDto.setRewardPoints(10);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(transactionrepository.findByCustomerIdAndDateBetween(customerId, startDate, endDate)).thenReturn(transactions);
        when(modelMapper.map(transaction, TransactionDto.class)).thenReturn(transactionDto);

        Map<String, Object> response = rewardService.getMontlyTransactionAndDetails(customerId, startDate, endDate);

        assertEquals(customer, response.get("customer"));
        assertTrue(response.containsKey("monthlyData"));
        assertNotNull(response.get("monthlyData"));

        verify(customerRepository).findById(customerId);
        verify(transactionrepository).findByCustomerIdAndDateBetween(customerId, startDate, endDate);
        verify(modelMapper).map(transaction, TransactionDto.class);
    }
}

	
	


