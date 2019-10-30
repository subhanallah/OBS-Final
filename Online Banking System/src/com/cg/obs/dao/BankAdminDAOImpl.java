package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Transaction;
import com.cg.obs.db.ConnectionFactory;
import com.cg.obs.exception.NoTransactionsException;

/**
 * The class {@code BankAdminDAOImpl} is an implementation of interface
 * {@code BankAdminDAO} that will connect our application to
 * existing database..
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
  
public class BankAdminDAOImpl implements BankAdminDAO{
	
	private final static Logger logger = Logger.getLogger(BankAdminDAOImpl.class);
	
	static Connection connection;
	public BankAdminDAOImpl() {
		PropertyConfigurator.configure(".\\resources\\log4j.properties");
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			logger.info("Class: BankAdminDAOImpl -> Method: BankAdminDAOImpl() : Setting Auto commit false.");
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: BankAdminDAOImpl() : SQLException while Setting Auto commit false.");
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves adminName from database as passed by the service layer
	 *
	 * @param adminName
	 * 		adminName is a String passed from main entered by user as adminName
	 *
	 * @return
	 * 		{@code userName} if the userName exists {@code String}
	 * 		equivalent to this string, {@code null} otherwise
	 *
	 */
	@Override
	public String selectAdminName(String adminName) {
		logger.info("Class: BankAdminDAOImpl -> Method: selectAdminName(String) : Selecting Admin Name.");
		String SQL = "SELECT ADMIN_NAME FROM BANK_ADMIN WHERE ADMIN_NAME = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, adminName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("ADMIN_NAME"));
				return rs.getString("ADMIN_NAME");
			}
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: selectAdminName(String) : SQLException while Selecting Admin Name.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves password from database as passed by the service layer
	 *
	 * @param adminName
	 * 		adminName is a String passed from main entered by user as adminName
	 *
	 * @param password
	 * 		password is a String passed from main entered by user as password
	 * 
	 * @return
	 * 		{@code password} if the userName exists {@code String}
	 * 		equivalent to this string, {@code null} otherwise
	 *
	 */
	@Override
	public String selectPassword(String adminName, String password) {
		logger.info("Class: BankAdminDAOImpl -> Method: selectPassword(String, String) : Selecting Password.");
		String SQL = "SELECT ADMIN_NAME,PASSWORD FROM BANK_ADMIN WHERE ADMIN_NAME = ? AND PASSWORD = ?";

		try (
				PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, adminName);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("ADMIN_NAME"));
				return rs.getString("PASSWORD");
			}
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: selectPassword(String, String) : SQLException while Selecting Password.");
			e.printStackTrace();
		}
		return null;

	}

	 /**
	 * Adds accountmaster in database as passed by the service layer
	 *
	 * @param accountMaster
	 * 		accountMaster is an object of AccountMaster as passed from service layer
	 * 
	 * @return
	 * 		{@code accountId} if the account is successfully added {@code integer}
	 * 		equivalent to this integer, {@code 0} otherwise
	 *
	 */
	@Override
	public int addAccountMaster(AccountMaster accountMaster) {
		logger.info("Class: BankAdminDAOImpl -> Method: addAccountMaster(AccountMaster) : Adding Account Master.");
		String SQL = "INSERT INTO ACCOUNT_MASTER(ACCOUNT_ID, ACCOUNT_TYPE, ACCOUNT_BALANCE, OPEN_DATE) VALUES(ACCOUNT_ID_SEQ.NEXTVAL,?,?,SYSDATE)";
		String select = "SELECT ACCOUNT_ID_SEQ.CURRVAL FROM DUAL";
		try(
				PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				Statement statement = connection.createStatement()){
			
			preparedStatement.setString(1, accountMaster.getAccountType());
			preparedStatement.setDouble(2, accountMaster.getOpeningBalance());
			preparedStatement.execute();
			ResultSet rs = statement.executeQuery(select);
			if (rs.next()) {
				int accountId = rs.getInt("CURRVAL");
				return accountId;
			}
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: addAccountMaster(AccountMaster) : SQLException while Adding Account Master.");
			System.out.println("BankAdminDAOImpl:addAccountMaster:"+e.getMessage());
		}
		return 0;
	}

	 /**
	 * Adds customer in database as passed by the service layer
	 *
	 * @param customer
	 * 		customer is an object of AccountMaster as passed from service layer
	 * 
	 * @return
	 * 		{@code true} if the account is successfully added {@code boolean}
     * 		equivalent to this boolean, {@code false} otherwise
	 *
	 */
	@Override
	public boolean addCustomer(Customer customer) {
		logger.info("Class: BankAdminDAOImpl -> Method: addCustomer(Customer) : Adding Customer.");
		String SQL = "INSERT INTO CUSTOMER(ACCOUNT_ID, CUSTOMER_NAME, MOBILE_NO, EMAIL, ADDRESS, PANCARD) VALUES(?,?,?,?,?,?)";
		try(
				PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			
			preparedStatement.setInt(1, customer.getAccountId());
			preparedStatement.setString(2, customer.getCustomerName());
			preparedStatement.setString(3, customer.getMobileNo());
			preparedStatement.setString(4, customer.getEmail());
			preparedStatement.setString(5, customer.getAddress());
			preparedStatement.setString(6, customer.getPanCardNo());
			int rows = preparedStatement.executeUpdate();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: addCustomer(Customer) : SQLException while Adding Customer.");
			System.out.println("BankAdminDAOImpl:addCustomer:"+e.getMessage());
		}
		return false;
	}

	 /**
	 * Adds user in database as passed by the service layer
	 *
	 * @param user
	 * 		user is an object of AccountHolder as passed from service layer
	 * 
	 * @return
	 * 		{@code true} if the account is successfully added {@code boolean}
	 * 		equivalent to this boolean, {@code false} otherwise
	 *
	 */
	@Override
	public boolean addUser(AccountHolder user) {
		logger.info("Class: BankAdminDAOImpl -> Method: addUser(AccountHolder) : Adding User.");
		
		String SQL = "INSERT INTO USER_TABLE(ACCOUNT_ID, USERNAME, PASSWORD, TRANSACTION_PASSWORD) VALUES(?,?,?,?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, user.getAccountId());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getTransactionPassword());
			int rows = preparedStatement.executeUpdate();
			connection.commit();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: addUser(AccountHolder) : SQLException while Adding User.");
			System.out.println("BankAdminDAOImpl:addUser:"+e.getMessage());
		}
		return false;
	}

	/**
	 * Extracts accountId based on pancardNo passed by the service layer if the user of that pan
	 * 		no is has an accountId
	 *
	 * @param panCardNo
	 * 		panCardNo is an string type as passed from service layer as panCardNo
	 * 
	 * @return
	 *	 	{@code accountId} if the panCard is successfully checked {@code integer}
	 * 		equivalent to this integer {@code 0} otherwise
	 *
	 */
	@Override
	public int checkPan(String panCardNo) {
		logger.info("Class: BankAdminDAOImpl -> Method: checkPan(String) : Checking Pan no.");
		String SQL = "SELECT ACCOUNT_ID FROM CUSTOMER WHERE PANCARD = '"+panCardNo+"'";
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			rs.next();
			return rs.getInt("ACCOUNT_ID");
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: checkPan(String) : SQLException while Checking Pan no.");
			System.out.println("BankAdminDAOImpl:checkPan:"+e.getMessage());
		}
		return 0;
	}

	/**
	 * Gets accountHolder object in database based on oldAccountId passed by the service layer
	 *
	 * @param oldAccountId
	 * 		oldAccountId is an integer type as passed from service layer as oldAccountId
	 * 
	 * @return
	 * 		{@code accountHolder} if the oldAccountId is successfully found {@code accountHolder }
	 * 		equivalent to this accountHolder {@code null} otherwise
	 *
	 */
	@Override
	public AccountHolder getUser(int oldAccountId) {
		logger.info("Class: BankAdminDAOImpl -> Method: getUser(int) : Getting User.");
		System.out.println(oldAccountId);
		String SQL = "SELECT * FROM USER_TABLE WHERE ACCOUNT_ID = "+oldAccountId;
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			if(rs.next()) {
			AccountHolder user = new AccountHolder();
			user.setUserName(rs.getString("USERNAME"));
			user.setLockStatus(rs.getString("LOCK_STATUS"));
			user.setPassword(rs.getString("PASSWORD"));
			user.setSecretAnswer(rs.getString("SECRET_ANSWER"));
			user.setTransactionPassword(rs.getString("TRANSACTION_PASSWORD"));
			user.setSecretQuestion(rs.getString("SECRET_QUESTION"));
			return user;}
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: getUser(int) : SQLException while Getting User.");
			System.out.println("BankAdminDAOImpl:getUser:"+e.getMessage());
		}
		return null;
	}

	/**
	 * Gets list of transactions in database based on date passed by the service layer
	 *
	 * @param date
	 * 		date is an Date type as passed from service layer as date
	 *
	 * @exception NoTransactionsException
	 * 		will be invoked if no transaction is done on aparticular date
	 * 
	 * @return
	 * 		{@code transaction} if the transactions are successfully found on that particualr date {@code list }
	 * 		equivalent to this list {@code null} otherwise
	 *
	 */
	@Override
	public List<Transaction> getTransations(java.util.Date date) throws NoTransactionsException {
		logger.info("Class: BankAdminDAOImpl -> Method: getTransations(java.util.Date) : Getting Transactions of a day.");
		String SQL = "SELECT * FROM TRANSACTIONS WHERE TO_DATE(TRANSACTION_DATE) = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			Date sDate = new Date(date.getTime());
			preparedStatement.setDate(1, sDate);
			ResultSet rs = preparedStatement.executeQuery();
			int count = 1;
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next() && count <= 10) {
				Transaction t = new Transaction();
				t.setAccountId(rs.getInt("ACCOUNT_ID"));
				t.setDateofTransaction(rs.getDate("TRANSACTION_DATE"));
				t.setTranAmount(rs.getDouble("TRAN_AMOUNT"));
				t.setTranDescription(rs.getString("TRAN_DESCRIPTION"));
				t.setTransactionId(rs.getInt("TRANSACTION_ID"));
				t.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				transactions.add(t);
				count++;
			}
			if(transactions == null || transactions.size() == 0)
				throw new NoTransactionsException("No Transactions Found For This Account.");
			return transactions;
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: getTransations(java.util.Date) : SQLException while Getting Transactions of a day.");
			System.out.println("AccountHolderDAOImpl:selectTentransactions: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Gets list of transactions in database based on date passed by the service layer
	 *
	 * @param startDate
	 * 		startDate is an Date type as passed from service layer as startDate
	 *
	 * @param endDate
	 * 		endDate is an Date type as passed from service layer as endDate
	 *
	 * @exception NoTransactionsException
	 * 				will be invoked if no transaction is done on aparticular date
	 * 
	 * @return {@code transaction} if the transactions are successfully found on that particualr date {@code list }
	 * 		equivalent to this list {@code null} otherwise
	 *
	 */
	@Override
	public List<Transaction> getTransations(java.util.Date startDate, java.util.Date endDate) throws NoTransactionsException {
		logger.info("Class: BankAdminDAOImpl -> Method: getTransations(java.util.Date, java.util.Date) : Getting Transactions between two dates.");
		try{
			Date sDate = new Date(startDate.getTime());
			Date eDate = new Date(endDate.getTime());
			String SQL = "SELECT * FROM TRANSACTIONS WHERE TRANSACTION_DATE BETWEEN ? AND ?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setDate(1, sDate);
			preparedStatement.setDate(2, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setAccountId(rs.getInt("ACCOUNT_ID"));
				
				t.setDateofTransaction(rs.getDate("TRANSACTION_DATE"));
				
				t.setTranAmount(rs.getDouble("TRAN_AMOUNT"));
				
				t.setTranDescription(rs.getString("TRAN_DESCRIPTION"));
				
				t.setTransactionId(rs.getInt("TRANSACTION_ID"));
				t.setTransactionType(rs.getString("TRANSACTION_TYPE"));
				transactions.add(t);
			}
			if(transactions == null || transactions.size() == 0)
				throw new NoTransactionsException("No Transactions Found For This Account.");
			return transactions;
		} catch (SQLException e) {
			logger.warn("Class: BankAdminDAOImpl -> Method: getTransations(java.util.Date, java.util.Date) : SQLException while Getting Transactions between two dates.");
			System.out.println("AccountHolderDAOImpl:selectTransactionsInDuration: " + e.getMessage());
		}
		return null;
	}

}
