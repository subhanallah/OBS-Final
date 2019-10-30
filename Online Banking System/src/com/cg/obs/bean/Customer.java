package com.cg.obs.bean;

/**
 * The class {@code Customer} will take the basic confidential details of Account Holder.  
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

//Customer bean to update all basic details
public class Customer {
	private int accountId;
	private String customerName;
	private String email;
	private String address;
	private String mobileNo;
	private String panCardNo;
	
	//Parameterised constructor...
	public Customer(String customerName, String email, String address, String mobileNo, String panCardNo) {
		super();
		this.customerName = customerName;
		this.email = email;
		this.address = address;
		this.mobileNo = mobileNo;
		this.panCardNo = panCardNo;
	}
	
	//Setters and getters for customerName,email,address,mobileNo,accountId, panCard
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPanCardNo() {
		return panCardNo;
	}
	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}
	@Override
	public String toString() {
		return "Customer [accountId=" + accountId + ", customerName=" + customerName + ", email=" + email + ", address="
				+ address + ", panCardNo=" + panCardNo + "]\n";
	}
	
	
}
