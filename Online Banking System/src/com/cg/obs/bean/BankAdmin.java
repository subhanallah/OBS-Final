package com.cg.obs.bean;

/**
 * The class {@code BankAdmin} will allow the administrator of this banking 
 * application to login and exhibit his control over creating new Customer Accounts and to 
 * retrieve Transaction details as per requirement.
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
  
//Bank Admin will able to access it...
public class BankAdmin {

	private String adminName;
	private String password;

	//Default constructor...
	public BankAdmin() {

	}

	//Parameterised Constructor...
	public BankAdmin(String adminName, String password) {
		this.adminName = adminName;
		this.password = password;
	}

	//Getter to set admin name..
	public String getAdminName() {
		return adminName;
	}

	//Setter to set admin name..
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	//Getter to get password..
	public String getPassword() {
		return password;
	}

	//Setter to set password..
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
