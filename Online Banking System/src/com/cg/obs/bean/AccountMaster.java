package com.cg.obs.bean;
/**
 * The class {@code AccountMaster} is used for mapping Account details of Account Holder with the database.  
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
import java.util.Date;

public class AccountMaster {
	private int accountId;
	private String accountType;
	private double openingBalance;
	private Date openDate;
	//Parameterised Constructor...
	public AccountMaster(String accountType, double openingBalance, Date openDate) {
		super();
		
		this.accountType = accountType;
		this.openingBalance = openingBalance;  
		this.openDate = openDate;
	}
	//Setters and getters for accountId,accountType,openingBalance, openDate
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	//To string function
	@Override
	public String toString() {
		return "AccountMaster [accountId=" + accountId + ", accountType=" + accountType + ", openingBalance="
				+ openingBalance + ", openDate=" + openDate + "]";
	}
	
	
}
