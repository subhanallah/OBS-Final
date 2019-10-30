package com.cg.obs.test;

import static org.junit.Assert.assertEquals;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;
import com.cg.obs.service.AccountHolderService;
import com.cg.obs.service.AccountHolderServiceImpl;


/**
 * The class {@code TestChangeAddress  } will test TestMiniStatement functionality..
 *
 * @GroupNo : 7
 * 
 * @Author1: Subhana- 46001890
 * @Author2: Atul Anand- 46001684
 * @Author3: Atal Tiwari- 46002419
 * @Author4: Survagya Agarwal- 46002233
 * 
 * @Version- 1.0
 * 
 * @Date-30/10/2019
 *   
 * */
public class TestMiniStatement {

	AccountHolderService accountHolderService;
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Mini Statement Testing");
		
	}
	@Before
	public void before() {
		System.out.println("Starting Testing");
		
		accountHolderService = new AccountHolderServiceImpl();
	}
	
	/**
	 * Will test the testMiniStatement functionality....
	 *
	 * @param expected
	 * 		expected is a long passed from main entered by user as expected
	 * 
	 * @param actual
	 * 	 actual	 is a String passed from main entered by user as actual
	 *
	 * return {@code true} if the userName exists {@code Boolean}
	 * 		equivalent to this Boolean, {@code false} otherwise
	 */
	
	@Test
	public void testMiniStatement() {
		Payee payee = new Payee();
		payee.setAccountId(1000000089);
		payee.setNickName("Subhana");
		payee.setPayeeAccountId(1000000100);
		int accountId = 1000000089;
		double amount = 100;
		try {
			accountHolderService.fundTransfer(accountId, payee, amount);
			List<Transaction> transactions = accountHolderService.getMiniStatement(accountId);
			
			Transaction t1 = transactions.get(0);
			boolean expected = true;
			boolean actual = false;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.parse("29/10/2019");
			
			if(t1.getAccountId()==accountId && t1.getTranAmount() == 100 && t1.getDateofTransaction().equals(date)) {
				actual = true;
			}
			assertEquals(expected, actual);
		} catch (LowBalanceException e) {
			e.printStackTrace();
		} catch (WrongPayeeAccountException e) {
			e.printStackTrace();
		} catch (NoTransactionsException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	@Test(expected=NoTransactionsException.class)
	public void testMiniStatementException() throws NoTransactionsException {
		List<Transaction> transactions = accountHolderService.getMiniStatement(1000000085);
	}
	@After
	public void after() {
		System.out.println("Testing Complete");
	}
	@AfterClass
	public static void afterClass() {
		System.out.println("Testing Completed successfully");
	}
}
