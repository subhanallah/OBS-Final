package com.cg.obs.bean;

import java.sql.Date;

/**
 * The class {@code Transaction} will keep the details required to complete the transaction 
 * and make them traceable. 
 * 
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

//Transaction bean starts here
public class Transaction {
	private int transactionId;
	private String tranDescription;
	private Date dateofTransaction;
	private String transactionType;
	private double tranAmount;
	private int accountId;
	
	//Getter to get transactionId
	public int getTransactionId() {
		return transactionId;
	}

	//Setter to set transactionId
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	//Getter to get transactionDescription
	public String getTranDescription() {
		return tranDescription;
	}

	//Setter to set transactionDescription
	public void setTranDescription(String tranDescription) {
		this.tranDescription = tranDescription;
	}


	//Getter to get date...
	public Date getDateofTransaction() {
		return dateofTransaction;
	}

	//Setter to set date...
	public void setDateofTransaction(Date dateofTransaction) {
		this.dateofTransaction = dateofTransaction;
	}

	//Getter to get typetransaction...
	public String getTransactionType() {
		return transactionType;
	}

	//Setter to Set typetransaction...
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	//getter to get tranAmount...
	public double getTranAmount() {
		return tranAmount;
	}

	//setter to set tranAmount...
	public void setTranAmount(double tranAmount) {
		this.tranAmount = tranAmount;
	}

	//Getter to get accountId...
	public int getAccountId() {
		return accountId;
	}

	//Setter to Set accountId...
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", tranDescription=" + tranDescription
				+ ", dateofTransaction=" + dateofTransaction + ", transactionType=" + transactionType + ", tranAmount="
				+ tranAmount + ", accountId=" + accountId + "]\n";
	}
	
	
}
