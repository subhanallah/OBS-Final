package com.cg.obs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.db.ConnectionFactory;
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
  

public class AccountHolderDAOImpl implements AccountHolderDAO {

	private final static Logger logger = Logger.getLogger(AccountHolderDAOImpl.class);
	
	private static Connection connection;
	public AccountHolderDAOImpl() {
		PropertyConfigurator.configure(".\\resources\\log4j.properties");
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			logger.info("Class: AccountHolderDAOImpl -> Method: AccountHolderDAOImpl() : Setting Auto commit false.");
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: AccountHolderDAOImpl() : Error while setting Auto commit false.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves userName from database as passed by the service layer
	 *
	 * @param userName
	 * 		userName is a String passed from main entered by user as username
	 *
	 * @return
	 * 		{@code userName} if the userName exists {@code String}
	 * 		equivalent to this string, {@code null} otherwise
	 *
	 */
	public String selectUserName(String userName) {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectUserName(String) : Selecting User Name.");
		String SQL = "SELECT USERNAME FROM USER_TABLE WHERE USERNAME = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, userName);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				
				return rs.getString("USERNAME");
			}
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: selectUserName(String) : SQL Exception while selecting User Name.");
			e.printStackTrace();
		}
		return null;
	}

	 /**
	 * Retrieves password from database as passed by the service layer
	 *
	 * @param userName
	 * 		userName is a String passed from main entered by user as userName
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
	public AccountHolder selectPassword(String userName, String password) {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectPassword(String) : Selecting Password.");
		String SQL = "SELECT ACCOUNT_ID,USERNAME,PASSWORD FROM USER_TABLE WHERE USERNAME = ? AND PASSWORD = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				//System.out.println(rs.getString("USER_NAME"));
				AccountHolder acc = new AccountHolder();
				acc.setAccountId(rs.getInt("ACCOUNT_ID"));
				acc.setUserName(userName);
				acc.setPassword(rs.getString("PASSWORD"));
				
				return acc;
			}
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: selectPassword(String) : SQLException while selecting Password.");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves list of payee from database based on the accountId of the account holder
	 *
	 * @param accountId
	 * 		accountId is a integer passed from main entered by user as accountId
	 *
	 * @return
	 *	 	{@code list} if the list of payees is not null {@code String}
	 * 		equivalent to this list, {@code null} otherwise
	 *
	 */
	@Override
	public List<Payee> selectAllPayee(int accountId) {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectAllPayee(int) : Selecting all Payee.");
		String SQL = "SELECT * FROM PAYEE_TABLE WHERE ACCOUNT_ID = ?";
		List<Payee> payeeList = new ArrayList<Payee>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Payee payee = new Payee();
				payee.setAccountId(accountId);
				payee.setPayeeAccountId(rs.getInt("PAYEE_ACCOUNT_ID"));
				payee.setNickName(rs.getString("NICK_NAME"));
				payeeList.add(payee);
				
			}
			return payeeList;
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: selectAllPayee(int) : SQLException while selecting all Payee.");
			System.out.println("AccountHolderDAOImpl:selectAllPayee: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Retrieves amount from database as passed by the service layer
	 * 
	 * @param accountId
	 * 		accountId is a integer passed from main entered by user as accountId
	 *
	 * @return
	 * 		{@code amountBalance} if the account balance is not equal to zero {@code integer}
	 * 		equivalent to this integer, {@code null} otherwise
	 */
	@Override
	public double getAmount(int accountId) {
		logger.info("Class: AccountHolderDAOImpl -> Method: getAmount(int) : Getting Amount.");
		String SQL = "SELECT ACCOUNT_BALANCE FROM ACCOUNT_MASTER WHERE ACCOUNT_ID = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			return rs.getDouble("ACCOUNT_BALANCE");
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: getAmount(int) : SQLException while Getting Amount.");
			System.out.println("AccountHolderDAOImpl:getAmount: " + e.getMessage());
		}
		return 0;
	}
	
	/**
	 * Retrieves payee from database based on the accountId and payeeAccountId of the account holder
	 *
	 * @param accountId
	 * 		accountId is a integer passed from main entered by user as accountId
	 *
	 * @param payeeAccountId
	 * 		payeeAccountId is a integer passed from main entered by user as payeeAccountId
	 * 
	 * @return
	 * 		{@code nickname} if the payee is present in the database {@code String}
	 * 		equivalent to this String, {@code null} otherwise
	 *
	 */
	@Override
	public String selectPayee(int accountId, int payeeAccountId) {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectPayee(int, int) : Selecting Payee.");
		String SQL = "SELECT * FROM PAYEE_TABLE WHERE ACCOUNT_ID = ? AND PAYEE_ACCOUNT_ID = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, payeeAccountId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			return rs.getString("NICK_NAME");
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: selectPayee(int, int) : SQLException while Selecting Payee.");
			System.out.println("AccountHolderDAOImpl:selectPayee: " + e.getMessage());
		}
		return null;
	}
	
	 /**
	 * Will update the account balance after withdrawing a certain amount
	 *
	 * @param accountId
	 * 		accountId is a integer passed from main entered by user as accountId
	 * 
	 * @param amount
	 * 		amount is a double passed from main entered by user as amount
	 *
	 * @return
	 * 		{@code true} if the updation of withdraw is successfull {@code boolean}
	 * 		equivalent to this boolean, {@code false} otherwise
	 *
	 */
	@Override
	public boolean withdraw(int accountId, double amount) {
		logger.info("Class: AccountHolderDAOImpl -> Method: withdraw(int, double) : Withdrawing.");
		String SQL = "UPDATE ACCOUNT_MASTER SET ACCOUNT_BALANCE = ACCOUNT_BALANCE - ? WHERE ACCOUNT_ID = ?";
		String insertTransaction = "INSERT INTO TRANSACTIONS(TRANSACTION_ID, TRAN_DESCRIPTION, TRANSACTION_DATE, TRANSACTION_TYPE, TRAN_AMOUNT, ACCOUNT_ID) "
				+ "VALUES(TRANSACTION_ID_SEQ.NEXTVAL,?, SYSDATE, 'debit', ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				PreparedStatement preparedStatement2 = connection.prepareStatement(insertTransaction)){
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, accountId);
			int rows = preparedStatement.executeUpdate();
			if(rows > 0) {
				preparedStatement2.setString(1, "Withdrawing amount="+amount);
				preparedStatement2.setDouble(2, amount);
				preparedStatement2.setInt(3, accountId);
				preparedStatement2.executeUpdate();
				return true;
			}
			else 
				return false;
			
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: withdraw(int, double) : SQLException while Withdrawing.");
			System.out.println("AccountHolderDAOImpl:withdraw: " + e.getMessage());
		}
		return false;
	}
	
	 /**
	 * Will update the account balance after depositing a certain amount
	 *
	 * @param accountId
	 * 		accountId is a integer passed from main entered by user as accountId
	 * 
	 * @param amount
	 * 		amount is a double passed from main entered by user as amount
	 *
	 * @return
	 * 		{@code true} if the updation of deposit is successful {@code boolean}
	 * 		equivalent to this boolean, {@code false} otherwise
	 *
	 */
	@Override
	public boolean deposit(int accountId, double amount) {
		logger.info("Class: AccountHolderDAOImpl -> Method: deposit(int, double) : Depositing.");
		String SQL = "UPDATE ACCOUNT_MASTER SET ACCOUNT_BALANCE = ACCOUNT_BALANCE + ? WHERE ACCOUNT_ID = ?";
		String insertTransaction = "INSERT INTO TRANSACTIONS(TRANSACTION_ID, TRAN_DESCRIPTION, TRANSACTION_DATE, TRANSACTION_TYPE, TRAN_AMOUNT, ACCOUNT_ID) "
				+ "VALUES(TRANSACTION_ID_SEQ.CURRVAL,?, SYSDATE, 'credit', ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL);
				PreparedStatement preparedStatement2 = connection.prepareStatement(insertTransaction)){
			preparedStatement.setDouble(1, amount);
			preparedStatement.setInt(2, accountId);
			int rows = preparedStatement.executeUpdate();
			if(rows > 0) {
				preparedStatement2.setString(1, "Depositing amount="+amount);
				preparedStatement2.setDouble(2, amount);
				preparedStatement2.setInt(3, accountId);
				preparedStatement2.executeUpdate();
				return true;
			}
			else 
				return false;
			
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: deposit(int, double) : SQLException while Depositing.");
			System.out.println("AccountHolderDAOImpl:deposit: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * As connection auto commit is set to false
	 * So this method will commit the changes.
	 */
	@Override
	public void connectionCommit() {
		logger.info("Class: AccountHolderDAOImpl -> Method: connectionCommit() : Committing connection.");
		try {
			connection.commit();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: connectionCommit() : SQLException while committing connection.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Will add a new payee to the database as passed by service
	 *
	 * @param payee
	 * 		payee is an object of Payee passed from main entered by user as payee
	 *
	 * @return
	 * 		{@code true} if payee is added successfully {@code boolean}
	 * 		equivalent to this boolean, {@code false} otherwise
	 *
	 */
	@Override
	public boolean addPayee(Payee payee) {
		logger.info("Class: AccountHolderDAOImpl -> Method: addPayee(Payee) : Adding Payee.");
		String SQL = "INSERT INTO PAYEE_TABLE VALUES(?,?,?)";
		System.out.println(payee.getAccountId());
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, payee.getAccountId());
			preparedStatement.setInt(2, payee.getPayeeAccountId());
			preparedStatement.setString(3, payee.getNickName());
			int rows = preparedStatement.executeUpdate();
			if(rows > 0)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: addPayee(Payee) : SQLException while adding payee.");
			System.out.println("AccountHolderDAOImpl:addPayee: " + e.getMessage());
		}
		return false;
	}
	
	/**
	 * Will add a new fund transfer detail to the database as passed by service
	 *
	 * @param payee
	 * 		payee is an object of Payee passed from main entered by user as payee
	 *
	 * @param amount
	 * 		amount is a double passed from main entered by user as amount
	 *
	 * @return
	 * 		void: does not return anything
	 */
	@Override
	public void addFundTransfer(Payee payee, double amount) {
		logger.info("Class: AccountHolderDAOImpl -> Method: addFundTransfer(Payee, double) : Adding Fund Transfer.");
		String SQL = "INSERT INTO FUND_TRANSFER(FUNDTRANSFER_ID, ACCOUNT_ID, PAYEE_ACCOUNT_ID, DATE_OF_TRANSFER, TRANSFER_AMOUNT) "
				+ "VALUES(FUNDTRANSFER_ID_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setInt(1, payee.getAccountId());
			preparedStatement.setInt(2, payee.getPayeeAccountId());
			preparedStatement.setDouble(3, amount);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: addFundTransfer(Payee, double) : SQLException while adding fund transfer.");
			System.out.println("AccountHolderDAOImpl:addFundTransfer: " + e.getMessage());
		}
	}
	
	 /**
	 * Retrieves list of all accounts from database based on the username entered by an account holder
	 *
	 * @param userName
	 * 		userName is a string passed from main entered by user as userName
	 *
	 * @return
	 * 		{@code list} if the list of all accounts of a user is not null {@code List}
	 * 		equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Integer> getAllAccounts(String userName) {
		logger.info("Class: AccountHolderDAOImpl -> Method: getAllAccounts(String) : Getting All Accounts.");
		String SQL = "SELECT ACCOUNT_ID FROM USER_TABLE WHERE USERNAME = '"+userName+"'";
		try(Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SQL);
			List<Integer> accounts = new ArrayList<Integer>();
			while(rs.next()) {
				accounts.add(rs.getInt("ACCOUNT_ID"));
			}
			return accounts;
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: getAllAccounts(String) : SQLException while getting all accounts.");
			System.out.println("AccountHolderDAOImpl:getAllAccounts: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Retrieves list of all transactions from database based on the accountNo entered by an account holder
	 *
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 *
	 * @exception NoTransactionsException
	 * 			If the user have not done any transaction
	 * 
	 * @return
	 * 		{@code list} if the list of all transaction of a user is not null {@code List}
	 * 		equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Transaction> selectTentransactions(int accountNo) throws NoTransactionsException {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectTentransactions(int) : Selecting  Ten Transactions.");
		String SQL = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = "+accountNo;
		try(Statement statement = connection.createStatement()){
			ResultSet rs = statement.executeQuery(SQL);
			int count = 1;
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next() && count <= 10) {
				Transaction t = new Transaction();
				t.setAccountId(accountNo);
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
			logger.error("Class: AccountHolderDAOImpl -> Method: selectTentransactions(int) : SQLException while Selecting  Ten Transactions.");
			System.out.println("AccountHolderDAOImpl:selectTentransactions: " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Retrieves list of all transactions made within a time duration from database
	 *
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 *
	 * @param startDate
	 * 			startDate is a date type passed from main entered by user as startDate
	 *
	 * @param lastDate
	 * 			lastDate is a date type passed from main entered by user as lastDate
	 *
	 * @exception NoTransactionsException
	 * 				If the user have not done any transaction
	 * 
	 * @return
	 * 			{@code list} if the list of all transaction of a user within a duration is not null {@code List}
	 * 			equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Transaction> selectTransactionsInDuration(int accountNo, java.util.Date startDate, java.util.Date endDate) throws NoTransactionsException {
		logger.info("Class: AccountHolderDAOImpl -> Method: selectTransactionsInDuration(int, java.util.Date, java.util.Date) : Selecting Transactins in a duration.");
		try{
			Date sDate = new Date(startDate.getTime());
			Date eDate = new Date(endDate.getTime());
			String SQL = "SELECT * FROM TRANSACTIONS WHERE ACCOUNT_ID = ? AND TRANSACTION_DATE BETWEEN ? AND ?";
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setInt(1, accountNo);
			preparedStatement.setDate(2, sDate);
			preparedStatement.setDate(3, eDate);
			ResultSet rs = preparedStatement.executeQuery();
			List<Transaction> transactions = new ArrayList<Transaction>();
			while(rs.next()) {
				Transaction t = new Transaction();
				t.setAccountId(accountNo);
				
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
			logger.error("Class: AccountHolderDAOImpl -> Method: selectTransactionsInDuration(int, java.util.Date, java.util.Date) : SQLException while Selecting Transactins in a duration.");
			System.out.println("AccountHolderDAOImpl:selectTransactionsInDuration: " + e.getMessage());
		}
		return null;
	}
	
	 /**
	  * Updates mobile number from database based on the accountNo and mobile number entered by an account holder
	  *
	  * @param accountNo
	  * 		accountNo is a integer passed from main entered by user as accountNo
	  *
	  * @param mobileNo
	  * 		mobileNo is a string passed from main entered by user as mobileNo
	  *
	  * @return
	  * 		{@code integer} if the user successfully updates the mobile number {@code List}
	  * 		equivalent to this integer, {@code 0} otherwise
	  */
	@Override
	public int updateMobileNo(int accountNo, String mobileNo) {
		logger.info("Class: AccountHolderDAOImpl -> Method: updateMobileNo(int, String) : Updating Mobile Number.");
		String SQL = "UPDATE CUSTOMER SET MOBILE_NO = ? WHERE ACCOUNT_ID = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, mobileNo);
			preparedStatement.setInt(2, accountNo);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: updateMobileNo(int, String) : SQLException while Updating Mobile Number.");
			System.out.println("AccountHolderDAOImpl:updateMobileNo: " + e.getMessage());
		}
		return 0;
	}
	
	 /**
	 * Updates address from database based on the accountNo and mobile number entered by an account holder
	 *
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 *
	 * @param address
	 * 			address is a string passed from main entered by user as address
	 *
	 * @return
	 * 			{@code integer} if the user successfully updates the address {@code List}
	 * 			equivalent to this integer, {@code 0} otherwise
	 */
	@Override
	public int updateAddress(int accountNo, String address) {
		logger.info("Class: AccountHolderDAOImpl -> Method: updateAddress(int, String) : Updating Address.");
		String SQL = "UPDATE CUSTOMER SET ADDRESS = ? WHERE ACCOUNT_ID = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, address);
			preparedStatement.setInt(2, accountNo);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: updateAddress(int, String) : SQLException while Updating Address.");
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}
	
	/**
	 * Will add request for a new cheque book based on the accountNo and number of pages entered by an account holder
	 *
	 * @param accountNo
	 * 			accountNo is a integer passed from main entered by user as accountNo
	 *
	 * @param numberofPages
	 * 			numberofPages is a string passed from main entered by user as numberofPages
	 *
	 * @return
	 * 			{@code integer} if the request is successful {@code Integer}
	 * 			equivalent to this integer, {@code 0} otherwise
	 */
	@Override
	public int addRequest(int accountNo, int numberOfPages) {
		logger.info("Class: AccountHolderDAOImpl -> Method: addRequest(int, int) : Adding reuest.");
		String SQL = "INSERT INTO SERVICE_TRACKER(SERVICE_ID, SERVICE_DESC, ACCOUNT_ID, RAISE_DATE, SERVICE_STATUS) "
				+ "VALUES(SERVICE_ID_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, "ChequeBook request");
			preparedStatement.setInt(2, accountNo);
			preparedStatement.setString(3, "new");
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: addRequest(int, int) : SQLException while Adding reuest.");
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}
	
	/**
	 * Updates password from database based on the userName entered by an account holder
	 *
	 * @param userName
	 * 		  userName is a integer passed from main entered by userName
	 *
	 * @param newPass
	 * 		  newPass is a string passed from main entered by user as newPass
	 *
	 * @return
	 * 		  {@code integer} if the user successfully updates the password {@code integer}
	 * 		  equivalent to this integer, {@code 0} otherwise
	 */
	@Override
	public int updatePass(String userName, String newPass) {
		logger.info("Class: AccountHolderDAOImpl -> Method: updatePass(String, String) : Updating Password.");
		String SQL = "UPDATE USER_TABLE SET PASSWORD = ? WHERE USERNAME = ? ";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, newPass);
			preparedStatement.setString(2, userName);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: updatePass(String, String) : SQLException while Updating Password.");
			System.out.println("AccountHolderDAOImpl:updateAddress: " + e.getMessage());
		}
		return 0;
	}
	
	/**
	 * Retrieves list of status of service requests from database based on the accountHolder object
	 *
	 * @param accountHolder
 	 *		accountHolder is a object type passed from main entered by user as accountHolder
	 *
	 * @return
	 * 		{@code list} if the list of all service request is not null {@code List}
	 * 		equivalent to this list, {@code null} otherwise
	 */
	@Override
	public List<Service> trackServiceRequest(AccountHolder accountHolder) {
		logger.info("Class: AccountHolderDAOImpl -> Method: trackServiceRequest(AccountHolder) : Tracking Service Request.");
		String SQL = "select SERVICE_ID, SERVICE_DESC, s.ACCOUNT_ID, RAISE_DATE, SERVICE_STATUS " + 
				"from SERVICE_TRACKER s " + 
				"INNER JOIN USER_TABLE u " + 
				"on s.ACCOUNT_ID = u.ACCOUNT_ID " + 
				"where u.USERNAME = ?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
			preparedStatement.setString(1, accountHolder.getUserName());
			ResultSet rs = preparedStatement.executeQuery();
			List<Service> serviceRequests = new ArrayList<Service>();
			while(rs.next()) {
				Service service = new Service();
				service.setAccountId(rs.getInt("ACCOUNT_ID"));
				service.setRaiseDate(rs.getDate("RAISE_DATE"));
				service.setServiceDesc(rs.getString("SERVICE_DESC"));				
				service.setServiceId(rs.getInt("SERVICE_ID"));
				service.setServiceStatus(rs.getString("SERVICE_STATUS"));
				serviceRequests.add(service);
			}
			return serviceRequests;
		} catch (SQLException e) {
			logger.error("Class: AccountHolderDAOImpl -> Method: trackServiceRequest(AccountHolder) : SQLException while Tracking Service Request.");
			System.out.println("AccountHolderDAOImpl:trackServiceRequest: " + e.getMessage());
		}
		return null;
	}
	
}
