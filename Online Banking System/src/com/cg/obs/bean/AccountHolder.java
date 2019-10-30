package com.cg.obs.bean;
/**
 * The class {@code AccountHolder} will allow the Account Holder to login into this banking 
 * application and fill the necessary security credentials.
 * 
/*
 * @GroupNo : 7
 * @Author1: Subhana- 46001890
 * @Author2: Atul Anand- 46001684
 * @Author3: Atal Tiwari- 46002419
 * @Author4: Survagya Agarwal- 46002233
 * @Version- 1.0
 * @Date-30/10/2019
 * 
 * */
public class AccountHolder {

	private int accountId;
	private String userName;
	private String password;
	private String secretQuestion;
	private String secretAnswer;
	private String transactionPassword;
	private String lockStatus;

	// Default Constructor..
	public AccountHolder() {

	}

	//Parameterised Constructor for login credantials...
	public AccountHolder(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;  
	}

	

	public String getSecretAnswer() {
		return secretAnswer;
	}


	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}


	public String getSecretQuestion() {
		return secretQuestion;
	}


	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}


	public String getTransactionPassword() {
		return transactionPassword;
	}


	public void setTransactionPassword(String transactionPassword) {
		this.transactionPassword = transactionPassword;
	}


	public String getLockStatus() {
		return lockStatus;
	}


	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}


	public AccountHolder(int accountId, String userName, String password) {
		super();
		this.accountId = accountId;
		this.userName = userName;
		this.password = password;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "AccountHolder [accountId=" + accountId + ", userName=" + userName + ", password=" + password
				+ ", secretQuestion=" + secretQuestion + ", secretAnswer=" + secretAnswer + ", transactionPassword="
				+ transactionPassword + ", lockStatus=" + lockStatus + "]\n";
	}
	
	
}
