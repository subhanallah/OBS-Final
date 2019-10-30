package com.cg.obs.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.obs.dao.AccountHolderDAO;
import com.cg.obs.dao.AccountHolderDAOImpl;

/**
 * The class {@code TestChangeAddress  } will test testChangeAddress..
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
public class TestChangeAddress {


	AccountHolderDAO accountHolderDAO;
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Address Change Testing");
		
	}
	@Before
	public void before() {
		accountHolderDAO = new AccountHolderDAOImpl();
	}
	
	/**
	 * Will test the changeAddress functionality....
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
	public void testChangeAddress() {
		int accountNo = 1000000089;
		String newAddress = "Pune";
		int expected = 1;
		int actual = accountHolderDAO.updateAddress(accountNo, newAddress);
		assertEquals(expected, actual);
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
