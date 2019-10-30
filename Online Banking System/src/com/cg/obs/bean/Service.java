package com.cg.obs.bean;

import java.sql.Date;

/**
 * The class {@code Service} will take the details required for tracking the status 
 * of any request raised by Account Holder. 
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

public class Service {
	private int serviceId;
	private String serviceDesc;
	private int accountId;
	private Date raiseDate;
	private String serviceStatus;
	
	//function to get all details in form of a string
	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", serviceDesc=" + serviceDesc + ", accountId=" + accountId
				+ ", raiseDate=" + raiseDate + ", serviceStatus=" + serviceStatus + "]\n";
	}
	
	//Getters and setters for serviceId,serviceDesc,accountId,raiseId, serviceStatus
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Date getRaiseDate() {
		return raiseDate;
	}
	public void setRaiseDate(Date raiseDate) {
		this.raiseDate = raiseDate;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
