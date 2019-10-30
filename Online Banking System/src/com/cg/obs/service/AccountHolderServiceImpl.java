package com.cg.obs.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.dao.AccountHolderDAO;
import com.cg.obs.dao.AccountHolderDAOImpl;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;


/**
 * The class {@code AccountHolderServiceImpl} is an implementation of interface
 * {@code AccountHolderService} that will connect this application to 
 * DAO layer.
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
public class AccountHolderServiceImpl implements AccountHolderService {

	private AccountHolderDAO accountHolderDAO = new AccountHolderDAOImpl();
	
	private final static Logger logger = Logger.getLogger(AccountHolderServiceImpl.class);

	/**
	 * Validates userName entered by account holder to login in his account
	 * 
	 * @param userName
	 * 			userName is a String passed from main entered by user as username
	 * 
	 * @return {@code true} if the username exists {@code String}
     *          equivalent to this string, {@code false} otherwise
	 * 			
	 * 	
	 */
	@Override
	public boolean validateUserName(String userName) {
		logger.info("Class: AccountHolderServiceImpl -> Method: validateUserName(String) : Validating UserName.");
		String result = accountHolderDAO.selectUserName(userName);
		if(result != null && result.equals(userName))
			return true;
		else
			return false;
	}
	
	/**
	 * Validates password entered by account holder to login in his account
	 * 
	 * @param userName
	 * 			userName is a String passed from main entered by user as username
	 * 
	 * @param password
	 *          password is a String passed from main entered by user as password
	 * 
	 * @return {@code true} if the password matches {@code String}
     *          equivalent to this string, {@code false} otherwise
	 * 			
	 * 	
	 */
	@Override
	public int validatePassword(String userName, String password) {
		logger.info("Class: AccountHolderServiceImpl -> Method: validatePassword(String,String) : Validating Password.");
		AccountHolder result = accountHolderDAO.selectPassword(userName, password);
		if(result != null && result.getAccountId() != 0 && result.getPassword().equals(password))
			return result.getAccountId();
		else
			return 0;
	}

	/**
	 * Get the entire list of payee in the list.
	 * 
	 * @param accountId
	 * 			accountId is a integer passed from main entered by user as username
	 *          
	 * 
	 * @return list
	 * 			{@code null} if the list is empty a {@code list}
     *          equivalent to this string, {@code list} otherwise
	 * 			
	 * 			
	 * 	
	 */
	@Override
	public List<Payee> getPayeeList(int accountId) {
		logger.info("Class: AccountHolderServiceImpl -> Method: getPayeeList(int) : Getting Payee list.");
		return accountHolderDAO.selectAllPayee(accountId);
	}

	/**
	 * Will transfer the fund from your account to payees account
	 * 
	 * @param accountId
	 * 			accountId is a integer passed from main entered by user as accountId
	 *  
	 * @param payee
	 *         payee is an object of payee type entered by user 
	 *         
	 * @param amount                
	 *         amount is a double value entered by user
	 * @return list
	 * 			{@code true} if the transfer is successful {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 			
	 * 	
	 */
	@Override
	public boolean fundTransfer(int accountId, Payee payee, double amount) throws LowBalanceException, WrongPayeeAccountException {
		logger.info("Class: AccountHolderServiceImpl -> Method: validateUserName(String) : Fund Transfer.");
		double availableBalance = accountHolderDAO.getAmount(accountId);
		String checkPayee = accountHolderDAO.selectPayee(accountId, payee.getPayeeAccountId());
		if(checkPayee == null || !payee.getNickName().equals(checkPayee) ){
			logger.error("Class: AccountHolderServiceImpl -> Method: fundTransfer(int,Payee,double) : Throwing WrongPayeeAccountException.");
			throw new WrongPayeeAccountException("You entered wrong payee account");
		}
		if(availableBalance < amount) {
			logger.error("Class: AccountHolderServiceImpl -> Method: fundTransfer(int,Payee,double) : Throwing LowBalanceException.");
			throw new LowBalanceException("You do not have sufficient balance.");
		}
		else {
			if(accountHolderDAO.withdraw(accountId, amount)) {
				if(accountHolderDAO.deposit(payee.getPayeeAccountId(), amount)) {
					accountHolderDAO.addFundTransfer(payee, amount);
					accountHolderDAO.connectionCommit();
					logger.info("Class: AccountHolderServiceImpl -> Method: fundTransfer(int,Payee,double) : Fund Transferred.");
					return true;
				}
				
			}
		}
		return false;
	}

	/**
	 * Will add payee details as entered by user
	 * 
	 * @param payee
	 *         payee is an object of payee type entered by user 
	 *         
	 * 
	 * @return 
	 * 			{@code true} if payee is added successfully {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
     *          
	 */
	@Override
	public boolean addPayee(Payee payee) {
		logger.info("Class: AccountHolderServiceImpl -> Method: addPayee(Payee) : Adding new Payee");
		if(accountHolderDAO.addPayee(payee)) {
			accountHolderDAO.connectionCommit();
			return true;
		}
		return false;
	}

	/**
	 * Will give the list of assocaited accounts.
	 * 
	 * @param userName
	 *         userName is an string type entered by user 
	 *         
	 * 
	 * @return 
	 * 			{@code list} if it finds a list {@code list}
     *          equivalent to this list, {@code null} otherwise
     *          
	 */
	@Override
	public List<Integer> getAssociatedAccount(String userName) {
		logger.info("Class: AccountHolderServiceImpl -> Method: getAssociatedAccount(String) : Getting All Accounts");
		return accountHolderDAO.getAllAccounts(userName);
	}

	/**
	 * Will give user the mini statement
	 * 
	 * @param accountNo
	 *         accountNo is an integer type entered by user 
	 *         
	 * @exception NoTransactionsException
	 *          If no transaction is done by the user 
	 *          
	 * @return 
	 * 			{@code list} if it finds a list {@code list}
	 *          equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Transaction> getMiniStatement(int accountNo) throws NoTransactionsException {
		logger.info("Class: AccountHolderServiceImpl -> Method: getMiniStatement(int) : Getting mini statement");
		return accountHolderDAO.selectTentransactions(accountNo);
	}

	/**
	 * Will give user the detailed statement in a particular time interval
	 * 
	 * @param accountNo
	 *         accountNo is an integer type entered by user 
	 *         
	 * @param startDate
	 *         startDate is a date type entered by user 
	 *         
	 * @param endDate
	 *         endDate is a date type entered by user        
	 *         
	 * @exception NoTransactionsException
	 *          If no transaction is done by the user 
	 *          
	 * @return 
	 * 			{@code list} if it finds a list {@code list}
	 *          equivalent to this list, {@code null} otherwise
	 *          
	 */
	@Override
	public List<Transaction> getDetailedTransactions(int accountNo, Date startDate, Date endDate) throws NoTransactionsException {
		logger.info("Class: AccountHolderServiceImpl -> Method: getDetailedTransactions(int,Date,Date) : Getting detailed statement");
		return accountHolderDAO.selectTransactionsInDuration(accountNo, startDate, endDate);
		
	}

	/**
	 * Will update the mobile number to a new value
	 * 
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 * 
	 *  @param mobileNo
	 * 			mobileNo is a string passed from main entered by user as mobileNo
	 * 
	 * @return boolean
	 * 			{@code true} if the update is successful {@code boolean}
	 *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */
	@Override
	public boolean changeMobileNo(int accountNo, String mobileNo) {
		logger.info("Class: AccountHolderServiceImpl -> Method: changeMobileNo(int,String) : Changing Mobile Number");
		int result = accountHolderDAO.updateMobileNo(accountNo, mobileNo);
		if(result == 1) {
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	/**
	 * Will update the address to a new value
	 * 
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 * 
	 *  @param address
	 * 			address is a string passed from main entered by user as address
	 * 
	 * @return boolean
	 * 			{@code true} if the update is successful {@code boolean}
	 *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */  
	@Override
	public boolean changeAddress(int accountNo, String address) {
		logger.info("Class: AccountHolderServiceImpl -> Method: changeAddress(int,String) : Changing Address");
		int result = accountHolderDAO.updateAddress(accountNo, address);
		if(result == 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	/**
	 * Will lodge a request for cheque book
	 * 
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 * 
	 *  @param numberOfPages
	 * 			numberOfPages is a integer passed from main entered by user as numberOfPages
	 * 
	 * @return boolean
	 * 			{@code true} if the request is successful {@code boolean}
	 *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */  
	@Override
	public boolean chequeBookRequest(int accountNo, int numberOfPages) {
		logger.info("Class: AccountHolderServiceImpl -> Method: chequeBookRequest(int,int) : Requesting for Cheque book.");
		int result = accountHolderDAO.addRequest(accountNo, numberOfPages);
		if(result == 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	/**
	 * Will change the password to a new value
	 * 
	 * @param userName
	 * 			userName is a string passed from main entered by user as userName
	 * 
	 *  @param newPass
	 * 			newPass is a string passed from main entered by user as newPass
	 * 
	 * @return boolean
	 * 			{@code true} if the update is successful {@code boolean}
	 *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */  
	@Override
	public boolean changePass(String userName, String newPass) {
		logger.info("Class: AccountHolderServiceImpl -> Method: changePass(String,String) : Changing password.");
		int result = accountHolderDAO.updatePass(userName, newPass);
		if(result >= 1)
		{
			accountHolderDAO.connectionCommit();
			return true;}
		else
			return false;
	}

	/**
	 * Will track the service request raised 
	 * 
	 * @param userName
	 * 			userName is a string passed from main entered by user as userName
	 * 
	 *  @param newPass
	 * 			newPass is a string passed from main entered by user as newPass
	 * 
	 * @return list
	 * 			{@code list} if it finds a list {@code list}
	 *          equivalent to this list, {@code null} otherwise
	 * 			
	 * 	
	 */  
	@Override
	public List<Service> trackServiceRequest(AccountHolder accountHolder) {
		logger.info("Class: AccountHolderServiceImpl -> Method: trackServiceRequest(AccountHolder) : Tracking Service Request.");
		List<Service> serviceRequests = accountHolderDAO.trackServiceRequest(accountHolder);
		return serviceRequests;
	}

	/**
	 * Will get the available balance associated with the given accountId
	 * 
	 * @param accountId
	 * 			accountId is an integer passed from main entered by user as accountId
	 * 
	 *
	 * @return double
	 * 			{@code double} if it finds a available balance{@code double}
	 *          present in the accountId, {@code null} otherwise
	 * 			
	 * 	
	 */  
	@Override
	public double getAvailableBalance(int accountId) {
		logger.info("Class: AccountHolderServiceImpl -> Method: getAvailableBalance(int) : Getting Available balance.");
		double availableBalance = accountHolderDAO.getAmount(accountId);
		return availableBalance;
	}

}
