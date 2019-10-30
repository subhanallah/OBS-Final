package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.dao.BankAdminDAO;
import com.cg.obs.dao.BankAdminDAOImpl;
import com.cg.obs.exception.NoTransactionsException;

/**
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
  
public class BankAdminServiceImpl implements BankAdminService {

	private BankAdminDAO bankAdminDAO = new BankAdminDAOImpl();
	private final static Logger logger = Logger.getLogger(BankAdminServiceImpl.class);

	/**
	 * Validates adminName entered by bank Admin to login in his account
	 *
	 * @param adminName
	 * 		adminName is a String passed from main entered by user as adminName
	 *
	 * return {@code true} if the userName exists {@code Boolean}
	 * 		equivalent to this Boolean, {@code false} otherwise
	 */
	@Override
	public boolean validateAdminName(String adminName) {
		logger.info("Class: BankAdminServiceImpl -> Method: validateAdminName(String) : Validating Admin Name.");
		String result = bankAdminDAO.selectAdminName(adminName);
		if (result != null && result.equals(adminName))
			return true;
		else {
			logger.warn("Class: BankAdminServiceImpl -> Method: validateAdminName(String) : Wrong Admin Name.");
			return false;
		}
	}

	/**
	 * Validates Password entered by bank Admin to login in his account
	 *
	 * @param adminName
	 * 			adminName is a String passed from main entered by user as adminName
	 * 
	 * @param password
	 * 			password is a String passed from main entered by user as password
	 * 
	 * return {@code true} if the password matches with that of the corresponding adminName {@code Boolean}
	 * 			equivalent to this boolean, {@code false} otherwise
	 */
	@Override
	public boolean validatePassword(String adminName, String password) {
		logger.info("Class: BankAdminServiceImpl -> Method: validateAdminName(String) : Validating Password.");
		String result = bankAdminDAO.selectPassword(adminName, password);
		if (result != null && result.equals(password))
			return true;
		else {
			logger.warn("Class: BankAdminServiceImpl -> Method: validateAdminName(String) : Wrong Password.");
			return false;
		}
	}

	 /**
	 * Will create an account by bank Admin based upon the customer details provided by the customer
	 *
	 * @param customer
	 * 			customer is a Object of Customer type passed from main entered by Customer as customer
	 * 
	 * @param accountMaster
	 * 			accountMaster is an Object of AccountMaster passed from main entered by Customer as accountMaster
	 * 
	 * return {@code true} if the account is sucessfully created {@code Boolean}
	 * 			equivalent to this boolean, {@code false} otherwise
	 */
	@Override
	public boolean createAccount(Customer customer, AccountMaster accountMaster) {
		logger.info("Class: BankAdminServiceImpl -> Method: createAccount(Customer, AccountMaster) : Creating Account.");
		boolean result = false;
		int oldAccountId = bankAdminDAO.checkPan(customer.getPanCardNo());
		int accountID = bankAdminDAO.addAccountMaster(accountMaster);
		if (accountID != 0) {
			accountMaster.setAccountId(accountID);
			System.out.println("Mobile: " + customer.getMobileNo());
			customer.setAccountId(accountID);
			if (bankAdminDAO.addCustomer(customer)) {
				
				AccountHolder user = new AccountHolder();
				user.setAccountId(accountID);
				if(oldAccountId == 0) {
					logger.info("Class: BankAdminServiceImpl -> Method: createAccount(Customer, AccountMaster) : No existing account id.");
					String mobile = customer.getMobileNo();
					user.setUserName(customer.getCustomerName().substring(0, 3).toLowerCase()
							+ mobile.substring(mobile.length() - 4, mobile.length()));
					user.setPassword(mobile.substring(mobile.length() - 5, mobile.length()));
					user.setTransactionPassword(customer.getCustomerName().substring(0, 2).toLowerCase()
							+ mobile.substring(mobile.length() - 3, mobile.length() - 1));
				}
				else {
					logger.info("Class: BankAdminServiceImpl -> Method: createAccount(Customer, AccountMaster) : Already an account present.");
					 user = bankAdminDAO.getUser(oldAccountId);
					 user.setAccountId(accountID);
				}
				if (bankAdminDAO.addUser(user)) {
					result = true;
					logger.info("Class: BankAdminServiceImpl -> Method: createAccount(Customer, AccountMaster) : User added.");
				}
			}
		}
		logger.info("Class: BankAdminServiceImpl -> Method: createAccount(Customer, AccountMaster) : Going Back after adding.");
		return result;
	}

	/**
	 * Will display the list of transactions done by the user on a particular date
	 *
	 * @param date
	 * 			date is a Date type passed from main entered by user as date
	 *
	 * @exception NoTransactionsException
	 * 			will be invoked if no transaction is done by a user on a particular day.
	 * 
	 * @return {@code list} if the list is not null {@code list}
	 * 			equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Transaction> displayTransactions(Date date) throws NoTransactionsException {
		logger.info("Class: BankAdminServiceImpl -> Method: displayTransactions(Date) : Displaying Transactions of a particular day.");
		return bankAdminDAO.getTransations(date);
	}

	/**
	 * Will display the list of transactions done by the user during a time interval
	 *
	 * @param startDate
	 * 			startDate is a Date type passed from main entered by user as startDate
	 * 
	 * @param lastDate
	 * 			lastDate is a Date type passed from main entered by user as lastDate
	 *
	 * @exception NoTransactionsException
	 * 			will be invoked if no transaction is done by a user on a particular duration.
	 * 
	 * @return {@code list} if the list is not null {@code list}
	 * 		equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Transaction> displayTransactions(Date startDate, Date endDate) throws NoTransactionsException {
		logger.info("Class: BankAdminServiceImpl -> Method: displayTransactions(Date, Date) : Displaying Transactions between start and end date.");
		return bankAdminDAO.getTransations(startDate, endDate);
	}

}
