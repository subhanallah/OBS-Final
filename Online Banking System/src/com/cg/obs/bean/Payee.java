package com.cg.obs.bean;

/**
 * The class {@code Payee} will take the account details of the other account holder
 *  with whom transaction should take place.  
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

public class Payee {  
	private int accountId;
	private int payeeAccountId;
	private String nickName;
	
	//Default constructor...
	public Payee() {
	}
	
	//Parameterised constructor...
	public Payee(int accountId, int payeeAccountId, String nickName) {
		super();
		this.accountId = accountId;
		this.payeeAccountId = payeeAccountId;
		this.nickName = nickName;
	}
	
	//Getter and setter for accountId, payeeAccountId,nickName...
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getPayeeAccountId() {
		return payeeAccountId;
	}
	public void setPayeeAccountId(int payeeAccountId) {
		this.payeeAccountId = payeeAccountId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Override
	public String toString() {
		return "Payee [accountId=" + accountId + ", payeeAccountId=" + payeeAccountId + ", nickName=" + nickName + "]\n";
	}
	
}
