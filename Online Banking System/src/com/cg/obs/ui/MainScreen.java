package com.cg.obs.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.cg.obs.bean.AccountHolder;
import com.cg.obs.bean.AccountMaster;
import com.cg.obs.bean.BankAdmin;
import com.cg.obs.bean.Customer;
import com.cg.obs.bean.Payee;
import com.cg.obs.bean.Service;
import com.cg.obs.bean.Transaction;
import com.cg.obs.exception.LowBalanceException;
import com.cg.obs.exception.NoTransactionsException;
import com.cg.obs.exception.WrongPayeeAccountException;
import com.cg.obs.service.AccountHolderService;
import com.cg.obs.service.AccountHolderServiceImpl;
import com.cg.obs.service.BankAdminService;
import com.cg.obs.service.BankAdminServiceImpl;
import com.cg.obs.test.TestCaseSuite;
import com.cg.obs.util.Utility;

/**
 * The class {@code MainScreen} is an the main class defined for this application
 * and would take all the necessary inputs from user and will direct the control to other classes of application
 * as required.
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


public class MainScreen {
	
	// Logger.....
	private final static Logger logger = Logger.getLogger(MainScreen.class);
	private static List<Integer> accounts;
	private static AccountHolderService accountHolderService = new AccountHolderServiceImpl();
	static BankAdminService bankAdminService = new BankAdminServiceImpl();
	private static Scanner scanner = new Scanner(System.in);
	private static Scanner sc = new Scanner(System.in);

	// Main Program starts from here...
	public static void main(String[] args) {

		Result result = JUnitCore.runClasses(TestCaseSuite.class);
		
	      System.out.println(result.wasSuccessful());
	      if(result.wasSuccessful()) {
	    	  System.out.println("All test cases passed");
	      }
	      else {
	    	  System.out.println("Failures are: ");
	    	  for (Failure failure : result.getFailures()) {
	 	         System.out.println(failure.toString());
	 	      }
	      }
		PropertyConfigurator.configure(".\\resources\\log4j.properties");
		
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(
				"****************************************************************WELCOME TO ONLINE BANKING SYSTEM****************************************************************");
		System.out.println(
				"----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();

		logger.info("Main screen loaded...");
		boolean run = true;

		int choice = 0;
		// We have three options to choose for entering inside program..
		while (run && choice != 3) {
			try {
				System.out.println("Select User type:");
				System.out.println("1. Press 1 for Account Holder.");
				System.out.println("2. Press 2 for Bank Admin.");
				System.out.println("3. Press 3 for Exit.");
				logger.info("Start menu loaded...");
				System.out.println();
				
				choice = scanner.nextInt();
				if (!Utility.validateChoice(choice)) {
					System.err.println("Invalid Choice. Please Try Again.");
					logger.warn("Class: MainScreen -> Method:main() : Invalid Choice entered"+choice);
					continue;
				}
				switch (choice) {
				case 1:
					accountHolder();
					break;
				case 2:
					bankAdmin();
					break;
				case 3:
					logger.info("Class: MainScreen -> Method:main() -> Exiting");
					System.out.println("Thank you for visiting us.");
				}
				
				
			} catch (InputMismatchException e) {
				logger.error("Class: MainScreen -> Method:main() : InputMismatchException occured");
				System.err.println("Invalid Input: java.lang.InputMismatchException");
				scanner.next();
			}

		}

	}

	
	/**
	 * To input and check login credentials from an accountholder
	 *
	 * @param userName
	 * userName is a String accepted as an input by the user
	 *
	 * @param password
	 * 		password is a String accepted as an input by the user
	 * 
	 * @param count
	 * 		count is an integer to allow only limited attempts by user
	 * 
	 * @return
	 * 		void: Does not return any value
	 */
	private static void accountHolder() {

		logger.info("Class: MainScreen -> Method: accountHolder() : Account Holder login page loaded.");
		String userName;
		String password;
		int flag1 = 1;
		int flag2 = 1;
		int count = 0;
		boolean check = false;
		int accountId;
		System.out.println("\nWelcome Account Holder.");
		System.out.println("Please enter your credentials for login:");
		do {
			do {
				System.out.print("UserName: ");
				userName = scanner.next();
				if (accountHolderService.validateUserName(userName))
					flag2 = 0;
				else {
					logger.warn("Class: MainScreen -> Method: accountHolder() : Invalid Username"+userName);
					System.err.println("Invalid Username. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			if (count == 5)
				return;
			flag2 = 1;
			count = 0;
			do {
				System.out.print("Password: ");
				password = scanner.next();
				accountId = accountHolderService.validatePassword(userName, password);
				if (accountId != 0) {
					flag2 = 0;
					System.out.println("Login Successful...");
					logger.info("Class: MainScreen -> Method: accountHolder() : Login successful");
					check = true;
				} else {
					logger.warn("Class: MainScreen -> Method: accountHolder() : Invalid Password");
					System.err.println("Invalid Password. Please try again.");
					System.out.println(4 - count + " attempts left.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			flag1 = 0;
		} while (flag1 != 0 && count < 5);
		if (check) {
			AccountHolder accountHolder = new AccountHolder(userName, password);
			accountHolderHome(accountHolder);
		} else {
			logger.error("Class:MainScreen -> Method: accountHolder() : Login failed...Exiting");
			System.err.println("Sorry login failed... Exiting...");
		}
	}

	 /**
	 * To input and check login credentials from a bankAdmin
	 *
	 * @param adminName
	 * 		adminName is a String accepted as an input by the bankAdmin
	 *
	 * @param password
	 * 		password is a String accepted as an input by the bankAdmin
	 * 
	 * @param count
	 * 		count is an integer to allow only limited attempts by bankAdmin
	 * 
	 * @return
	 * 		void: Does not return any value
	 */
	private static void bankAdmin() {
		logger.info("Class: MainScreen -> Method: bankAdmin() : Account Holder login page loaded.");
		String adminName;
		String password;
		int flag1 = 1;
		int flag2 = 1;
		int count = 0;
		boolean check = false;
		System.out.println("\nWelcome Bank Admin");
		System.out.println("Please enter your credentials for login:");
		do {
			do {
				System.out.print("AdminName: ");
				adminName = scanner.next();
				if (bankAdminService.validateAdminName(adminName))
					flag2 = 0;
				else {
					logger.warn("Class: MainScreen -> Method: bankAdmin() : Invalid admin name."+adminName);
					System.err.println("Invalid Admin name. Please try again.");
					System.out.println(4 - count + " attempts left.");
					count++;
				}
			} while (flag2 != 0 && count < 5);
			if (count == 5)
				return;
			flag2 = 1;
			count = 0;
			do {
				System.out.print("Password: ");
				password = scanner.next();
				if (bankAdminService.validatePassword(adminName, password)) {
					flag2 = 0;
					System.out.println("Login Successful...");
					logger.info("Class: MainScreen -> Method: bankAdmin() : Login Successful");
					check = true;
				} else {
					logger.warn("Class: MainScreen -> Method: bankAdmin() : Invalid password.");
					System.err.println("Invalid Password. Please try again.");
					System.out.println(4 - count + " attempts more.");
					count++;
				}

			} while (flag2 != 0 && count < 5);
			flag1 = 0;
		} while (flag1 != 0 && count < 5);
		if (check) {
			BankAdmin bankAdmin = new BankAdmin(adminName, password);
			bankAdminHome(bankAdmin);
		} else {
			logger.error("Class: MainScreen -> Method: bankAdmin() : Login failed... going back.");
			System.err.println("Login failed... going back...");
		}
	}

	/**
	 * To display menu to a bankAdmin and perform actions based according to the choice entered by bankAdmin
	 *
	 * @param choice
	 *	 	choice is a integer accepted as an input by the bankAdmin
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void bankAdminHome(BankAdmin bankAdmin) {
		logger.info("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : BankAdmin Home loaded.");
		System.out.println("\nWelcome " + bankAdmin.getAdminName() + " to the Online Banking System ADMIN PORTAL");
		System.out.println();
		int choice = 0;

		do {
			try {
				System.out.println("Press 1 for Creating New Account.");
				System.out.println("Press 2 for Displaying transactions of all accounts.");
				System.out.println("Press 3 for Log Out.");
				System.out.println("Press 4 to exit from the application.");
				
				choice = scanner.nextInt();
	
				switch (choice) {
				case 1:
					createAccount();
					break;
				case 2:
					displayTransactionsToAdmin();
					break;
				case 3:
					System.out.println("successfully logged out...");
					logger.info("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : BankAdmin Home logging out.");
					break;
				case 4:
					System.out.println("Exiting from the application...");
					logger.info("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : Exiting from the application.");
					System.exit(0);
				default:
					System.out.println("Invalid Input... Please try again(Enter correct Integer)...");
					logger.warn("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : Invalid Input: " + choice);
				}
			}catch(InputMismatchException e) {
				System.err.println("Invalid Input(java.lang.InputMismatchException): Please Enter correct Integer value.");
				logger.error("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : InputMismatchException occurred.");
				scanner.next();
			}
		} while (choice != 3);

	}

	/**
	 * To display menu to a bankAdmin and perform actions based according to the choice entered by bankAdmin
	 *
	 * @param choice
	 * 		choice is a integer accepted as an input by the bankAdmin
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void displayTransactionsToAdmin() {
		logger.info("Class: MainScreen -> Method: displayTransactionsToAdmin() : Display Transaction To Admin.");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		int choice = 0;
		do {
			try {
				System.out.println("Press 1 for Displaying transactions of a particular day.");
				System.out.println("Press 2 for Displaying transactions in a duration.");
				System.out.println("Press 3 to go back.");
				System.out.println("Press 4 to exit from the application.");

				choice = scanner.nextInt();
				List<Transaction> transactions;
	
				if (choice == 1) {
					logger.info("Class: MainScreen -> Method: displayTransactionsToAdmin() : Display Transaction for a particular day.");
					System.out.println("Enter the date in the format dd/MM/yyyy");
					Date date;
					try {
						date = df.parse(scanner.next());
						transactions = bankAdminService.displayTransactions(date);
						System.out.println(transactions);
					} catch (ParseException e) {
						System.err.println("Please Enter correct date.");
						logger.error("Class: MainScreen -> Method: displayTransactionsToAdmin() : ParseException occurred.");
					} catch (NoTransactionsException e) {
						System.err.println("No Transactions found.");
						logger.error("Class: MainScreen -> Method: displayTransactionsToAdmin() : No Transactions found Exception.");
					}
	
				} else if (choice == 2) {
					logger.info("Class: MainScreen -> Method: displayTransactionsToAdmin() : Display Transaction in a duration.");
					System.out.println("Enter start date in the format dd/mm/yyyy: ");
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					sdf.setLenient(false);
					Date startDate;
					try {
						startDate = sdf.parse(scanner.next());
						System.out.println("Enter end date in the format dd/mm/yyyy: ");
						Date endDate = sdf.parse(scanner.next());
						transactions = bankAdminService.displayTransactions(startDate, endDate);
						System.out.println(transactions);
					} catch (ParseException e) {
						System.err.println("Please Enter correct date.");
						logger.error("Class: MainScreen -> Method: displayTransactionsToAdmin() : ParseException occurred.");
					} catch (NoTransactionsException e) {
						System.err.println("No Transactions found.");
						logger.error("Class: MainScreen -> Method: displayTransactionsToAdmin() : No Transactions found Exception.");
					}
	
				} else if (choice == 3) {
					System.out.println("Going back...");
					logger.info("Class: MainScreen -> Method: displayTransactionsToAdmin() : Going Back.");
				} else if (choice == 4) {
					System.out.println("Exiting from the application...");
					logger.info("Class: MainScreen -> Method: displayTransactionsToAdmin() : Exiting from the application");
					System.exit(0);
				} else {
					System.err.println("Invalid Input: Please Enter correct input.");
					logger.warn("Class: MainScreen -> Method: displayTransactionsToAdmin() : Invalid input entered.");
				}
			}catch(InputMismatchException e) {
				System.err.println("Invalid Input(java.lang.InputMismatchException): Please Enter correct Integer value.");
				logger.error("Class: MainScreen -> Method: bankAdminHome(BankAdmin) : InputMismatchException occurred.");
				scanner.next();
			}
		} while (choice != 3);

	}

	/**
	 * This will allow bankAdmin to create new account by asking details of particular person...
	 *
	 * @param name
	 *	 	name is a String accepted as an input by the bankAdmin
	 *
	 * @param address
	 * 		address is a String accepted as an input by the bankAdmin
	 *
	 * @param mobileNo
	 * 		mobileNo is a String accepted as an input by the bankAdmin
	 * 
	 * @param emailId
	 * 		emailId is a String accepted as an input by the bankAdmin
	 * 
	 * @param accountType
	 * 		accountType is a String accepted as an input by the bankAdmin
	 * 
	 * @param panCardNo
	 * 		panCardNo is a String accepted as an input by the bankAdmin
	 * 
	 * @return
	 * 		void: Does not return any value
	 */
	private static void createAccount() {
		logger.error("Class: MainScreen -> Method: createAccount() : Create Account Method");
		sc.useDelimiter("\n");
		String name = null;
		String address = null;
		String mobileNo = null;
		String emailId = null;
		String accountType = null;
		String panCardNo = null;
		int count = 3;
		double openingBalance = 0;
		boolean check = false;
		while(count > 0) {
			System.out.println("Enter Account Holder Name:");
			name = sc.next();
			if (!Utility.validateName(name)) {
				System.out.println("Invalid name... Name should be at least of size 3");
				logger.warn("Class: MainScreen -> Method: createAccount() : Invalid name entered.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		System.out.println("Enter Address:");
		address = sc.next();
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		count = 3;
		check = false;
		while (count > 0) {
			System.out.println("Enter Mobile Number:");
			mobileNo = scanner.next();
			if (!Utility.validateMobileNumber(mobileNo)) {
				System.out.println("Invalid Mobile Number...");
				logger.warn("Class: MainScreen -> Method: createAccount() : Invalid mobile number entered.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		count = 3;
		check = false;
		while (count > 0) {
			System.out.println("Enter Email-Id:");
			emailId = scanner.next();
			if (!Utility.validateEmailId(emailId)) {
				System.out.println("Invalid email ID...");
				logger.warn("Class: MainScreen -> Method: createAccount() : Invalid email id entered.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		count = 3;
		check = false;
		while (count > 0) {
			System.out.println("Enter Account Type:");
			accountType = scanner.next();
			if (!Utility.validateAccountType(accountType)) {
				System.out.println("Invalid account type...");
				logger.warn("Class: MainScreen -> Method: createAccount() : Invalid account type entered.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		count = 3;
		check = false;
		while (count > 0) {
			System.out.println("Enter Pan Card Number:");
			panCardNo = scanner.next();
			panCardNo = panCardNo.toUpperCase();
			if (!Utility.validatePanNumber(panCardNo)) {
				System.out.println("Invalid PAN...");
				logger.warn("Class: MainScreen -> Method: createAccount() : Invalid pan number entered.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}

		
		if (!check) {
			logger.info("Class: MainScreen -> Method: createAccount() : Going Back.");
			return;
		}
		count = 3;
		check = false;
		while (count > 0) {
			System.out.println("Enter opening balance:");
			openingBalance = scanner.nextDouble();
			if (!Utility.validateOpeningBalance(openingBalance)) {
				System.out.println("Openinig Balance cannot be negative...");
				logger.warn("Class: MainScreen -> Method: createAccount() : Negative openning balance.");
				count--;
				System.out.println(count + " attempts left.");
			} else {
				count = 0;
				check = true;
			}
		}
		Date today = new Date();
		Customer customer = new Customer(name, emailId, address, mobileNo, panCardNo);
		AccountMaster accountMaster = new AccountMaster(accountType, openingBalance, today);
		if (bankAdminService.createAccount(customer, accountMaster)) {
			System.out.println("Account created successfully...");
			logger.info("Class: MainScreen -> Method: createAccount() : Account created and Going Back.");
		}
		else {
			System.err.println("Error while creating account. Please try again...");
			logger.info("Class: MainScreen -> Method: createAccount() : Account not created and Going Back.");
		}

	}

	/**
	 * To display menu to a accountHolder and perform actions based according to the choice entered by accountHolder
	 *
	 * @param choice
	 * 		choice is a integer accepted as an input by the bankAdmin
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void accountHolderHome(AccountHolder accountHolder) {
		int choice = 0;
		logger.info("Class: MainScreen -> Method: accountHolderHome(AccountHolder) : Account Holder Home.");
		System.out.println("\nWelcome " + accountHolder.getUserName() + " to the Online Banking System USER PORTAL:");
		System.out.println();
		accounts = accountHolderService.getAssociatedAccount(accountHolder.getUserName());
		System.out.println("Your accounts:");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();

		do {
			try {
				System.out.println("Press 1 for View Mini/Detailed statement.");
				System.out.println("Press 2 for Change in address/mobile number.");
				System.out.println("Press 3 for Request for cheque book.");
				System.out.println("Press 4 for Track service request.");
				System.out.println("Press 5 for Fund Transfer.");
				System.out.println("Press 6 for Change password.");
				System.out.println("Press 7 for Log Out.");
				System.out.println("Press 8 to exit from the application.");
				
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					getTransactions();
					break;
				case 2:
					changeDetails();
					break;
				case 3:
					chequeBookRequest();
					break;
				case 4:
					trackServiceRequest(accountHolder);
					break;
				case 5:
					fundTransfer(accountHolder);
					break;
				case 6:
					changePassword(accountHolder);
					break;
				case 7:
					System.out.println("successfully logged out...");
					logger.info("Class: MainScreen -> Method: accountHolderHome(AccountHolder) : Logging Out.");
					break;
				case 8:
					System.out.println("Exiting fron the application.");
					logger.info("Class: MainScreen -> Method: accountHolderHome(AccountHolder) : Exiting From the application.");
					System.exit(0);
				default:
					System.err.println("Invalid Input... Please try again...");
					logger.warn("Class: MainScreen -> Method: accountHolderHome(AccountHolder) : Invalid Choice Entered.");
				}
			}catch(InputMismatchException e) {
				System.err.println("Invalid Input(java.lang.InputMismatchException): Please Enter correct Integer value.");
				logger.error("Class: MainScreen -> Method: accountHolderHome(AccountHolder) : Input Mismatch Exception occurred.");
				scanner.next();
			}
		} while (choice != 7);
	}

	 /**
	 * To track the status of request of a chequebook raised by accountholder
	 *
	 * @param accountHolder
	 * 		accountHolder is an object of AccountHolder accepted as an input.
	 * 		choice is a integer accepted as an input by the bankAdmin
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void trackServiceRequest(AccountHolder accountHolder) {
		logger.info("Class: MainScreen -> Method: trackServiceRequest(AccountHolder) : Account Holder trackServiceRequest.");
		List<Service> serviceRequests = accountHolderService.trackServiceRequest(accountHolder);
		System.out.println(serviceRequests);

	}

	/**
	 * To change the password as required by accountHolder
	 *
	 * @param accountHolder
	 *		 accountHolder is an object of AccountHolder accepted as an input.
	 *
	 * @param oldPass
	 * 		oldPass is a integer accepted as an input by the accountHolder
	 * 
	 * @param newPass
	 * 		newPass is a integer accepted as an input by the accountHolder
	 * 
	 * @return
	 * 		void: Does not return any value
	 */
	private static void changePassword(AccountHolder accountHolder) {
		logger.info("Class: MainScreen -> Method: changePassword(AccountHolder) : Account Holder changing password.");
		System.out.println("Enter current password: ");
		String oldPass = scanner.next();
		if (oldPass.equals(accountHolder.getPassword())) {
			System.out.println("Enter new password:");
			String newPass = scanner.next();
			int count = 0;
			while (count < 3) {
				System.out.println("Confirm password:");
				if (newPass.equals(scanner.next())) {
					count = 3;
					accountHolderService.changePass(accountHolder.getUserName(), newPass);
				} else {
					logger.warn("Class: MainScreen -> Method: changePassword(AccountHolder) :  wrong old password.");
					count++;
				}
			}
			System.out.println("Password Changed successfully");
			logger.info("Class: MainScreen -> Method: changePassword(AccountHolder) : Password changed");
		} else {
			System.out.println("Wrong password :-(");
			logger.info("Class: MainScreen -> Method: changePassword(AccountHolder) : Password change failed... Going back.");
		}
	}

	 /**
	 * To raise the request of cheque book as required by accountHolder
	 *
	 * @param numberOfPages
	 * 		numberOfPages is a integer accepted as an input by the accountHolder
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void chequeBookRequest() {
		logger.info("Class: MainScreen -> Method: chequeBookRequest() : Requesting for cheque book.");
		boolean count = false;
		int numberOfPages = 0;
		while (!count) {
			System.out.println("Enter number of pages you require (50,100,200).");
			numberOfPages = scanner.nextInt();
			if (Utility.validateNoOfPages(numberOfPages)) {
				logger.warn("Class: MainScreen -> Method: chequeBookRequest() : Invalid number of pages." + numberOfPages);
				count = true;
			}
		}
		System.out.println("Enter index of account for which you want to request cheque book.");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();
		int accountIndex = scanner.nextInt();
		int accountNo;
		accountNo = accounts.get(accountIndex - 1);
		accountHolderService.chequeBookRequest(accountNo, numberOfPages);
		System.out.println("Request logged for a cheque book with " + numberOfPages + " pages for account no. " + accountNo);
		logger.info("Class: MainScreen -> Method: chequeBookRequest() : Cheque book Request logged for account number" + accountNo);
	}

	 /**
	 * This function will allow the Account Holder to change Mobile Number/Address.....
	 *
	 * @param choice
	 * 		choice is a integer accepted as an input by the accountHolder
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void changeDetails() {
		logger.info("Class: MainScreen -> Method: changeDetails() : Changing Address or mobile number");
		System.out.println("1.Change mobile number\n2.Change Address");
		int choice = scanner.nextInt();
		System.out.println("Enter index of account for which you want change Mobile Number or Address:");
		Iterator<Integer> itr = accounts.iterator();
		int index = 1;
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		System.out.println();
		int accountIndex = scanner.nextInt();
		int accountNo;
		accountNo = accounts.get(accountIndex - 1);
		System.out.println(accountNo);
		if (choice == 1) {
			System.out.println("Enter new Mobile Number: ");
			String mobileNo = scanner.next();
			if (accountHolderService.changeMobileNo(accountNo, mobileNo)) {
				System.out.println("Mobile Number changed successfully.");
				logger.info("Class: MainScreen -> Method: changeDetails() : Mobile number changed");
			}
			else {
				System.err.println("Mobile Number not changed.");
				logger.info("Class: MainScreen -> Method: changeDetails() : Mobile number can't be changed");
			}
		} else if (choice == 2) {
			System.out.println("Enter new address: ");

			sc.useDelimiter("\n");
			String address = sc.next();

			if (accountHolderService.changeAddress(accountNo, address)) {
				System.out.println("Address changed successfully.");
				logger.info("Class: MainScreen -> Method: changeDetails() : Address changed");
			}
			else {
				System.err.println("Address not changed.");
				logger.info("Class: MainScreen -> Method: changeDetails() : Address can't be changed");
			}
		} else {
			logger.info("Class: MainScreen -> Method: changeDetails() : Invalid Choice entered");
			System.err.println("Invalid Input");
		}

	}

	/**
	 * This method will show Mini Statement/ Detailed statement between selected dates....
	 *
	 * @param choice
	 * 		choice is a integer accepted as an input by the accountHolder
	 *
	 * @return
	 * 		void: Does not return any value
	 */
	private static void getTransactions() {
		logger.info("Class: MainScreen -> Method: getTransactions() : User getting transactions.");
		do {
			try {
				
				System.out.println("1.Mini statement\n2.Detailed Statement\n3.Go Back");
				int choice = scanner.nextInt();
				int accountNo;
				System.out.println("Enter index of account for which you want to get Transactions");
				Iterator<Integer> itr = accounts.iterator();
				int index = 1;
				while (itr.hasNext()) {
					System.out.println((index++) + ". Account Number: " + itr.next());
				}
				int accountIndex = scanner.nextInt();
				System.out.println();
				accountNo = accounts.get(accountIndex - 1);
				System.out.println(accountNo);
				if (choice == 1) {
					List<Transaction> transactions = accountHolderService.getMiniStatement(accountNo);
					System.out.println(transactions);
					logger.info("Class: MainScreen -> Method: getTransactions() : User getting mini statement.");
				} else if (choice == 2) {
					Date startDate;
					Date endDate;
					int count = 1;
					while (count <= 5) {
						try {
							System.out.println("Enter start date in the format dd/mm/yyyy: ");
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							sdf.setLenient(false);
							startDate = sdf.parse(scanner.next());
							System.out.println("Enter end date in the format dd/mm/yyyy: ");
							endDate = sdf.parse(scanner.next());
							List<Transaction> transactions = accountHolderService.getDetailedTransactions(accountNo,
									startDate, endDate);
							System.out.println(transactions);
							break;
						} catch (ParseException e) {
							System.err.println("You have not entered date is invalid.\n" + (5 - count)
									+ " attempts left.");
							logger.error("Class: MainScreen -> Method: getTransactions() : Parse Exception occurred.");
							count++;
						}
					}
	
				} else if (choice == 3) {
					logger.info("Class: MainScreen -> Method: getTransactions() : Going Back to previous menu..");
					System.out.println("Going Back to previous menu.");
					break;
				}
				else {
					logger.info("Class: MainScreen -> Method: getTransactions() : Invalid Input");
					System.err.println("Invalid Input");
				}
			} catch (NoTransactionsException e) {
				logger.error("Class: MainScreen -> Method: getTransactions() : No Transactions Exception occurred.");
				System.err.println("No Transactions found " + e.getMessage());
			} catch(InputMismatchException e) {
				System.err.println("Invalid Input(java.lang.InputMismatchException): Please Enter correct Integer value.");
				logger.error("Class: MainScreen -> Method: getTransactions() : Input Mismatch Exception occurred.");
				scanner.next();
			} catch (Exception e) {
				logger.error("Class: MainScreen -> Method: getTransactions() : Exception occurred.");
				System.err.println("Error while getting associated account with the index:" + e.getMessage());
			} 
		} while(true);
	}

	 /**
	 * This method will allow account Holder to transfer money from his/her account to another account..
	 * @param choice
	 * 		choice is a integer accepted as an input by the accountHolder
	 * @return
	 * 		void: Does not return any value
	 */
	private static void fundTransfer(AccountHolder accountHolder) {
		logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Transferring fund.");
		int choice = 0;
		System.out.println();
		System.out.println("Select index of account from which you want to transfer the money.");
		int index = 1;
		Iterator<Integer> itr = accounts.iterator();
		while (itr.hasNext()) {
			System.out.println((index++) + ". Account Number: " + itr.next());
		}
		int accountIndex = scanner.nextInt();
		int accountNo = accounts.get(accountIndex - 1);
		accountHolder.setAccountId(accountNo);
		do {
			try {
				System.out.println("1. Transfer in existing payee");
				System.out.println("2. Add new payee");
				System.out.println("3. Go Back");
				System.out.println("4. Exit from the application.");
				
				choice = scanner.nextInt();
	
				switch (choice) {
				case 1:
					List<Payee> payeeList = accountHolderService.getPayeeList(accountHolder.getAccountId());
					if (payeeList == null || payeeList.size() == 0)
						System.err.println("You have not added any payee yet. Please add a payee to transfer money.");
					else {
						System.out.println(payeeList);
						Payee payee = new Payee();
						System.out.println("Enter payee account number: ");
						payee.setPayeeAccountId(scanner.nextInt());
						System.out.println("Enter nick name of payee: ");
						payee.setNickName(scanner.next());
						payee.setAccountId(accountHolder.getAccountId());
						System.out.println(payee);
						System.out.println("Available Balance: " + accountHolderService.getAvailableBalance(accountHolder.getAccountId()));
						System.out.println("Enter amount: ");
						double amount = 0;
						int count = 3;
						while(count > 0) {
							try {
							amount = scanner.nextDouble();
							break;
							}catch(InputMismatchException e) {
								System.err.println("Amount can not be string...");
								scanner.next();
								count--;
							}
						}if(count == 0 || amount == 0)
							return;
						try {
							if (accountHolderService.fundTransfer(accountHolder.getAccountId(), payee, amount)) {
								System.out.println("Amount: " + amount + " transferred succesfully to " + payee.getAccountId());
								logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Fund transferred successfully.");
								System.out.println("Available Balance: " + accountHolderService.getAvailableBalance(accountHolder.getAccountId()));
							}
							else {
								logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Transfer failed.");
								System.err.println("Can't Transfer");
							}
						} catch (LowBalanceException e) {
							System.err.println("Not Enough balance " + e.getMessage());
							logger.error("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Low Balance Exception Occurred.");
						} catch (WrongPayeeAccountException e) {
							System.err.println("Wrong Payee Account " + e.getMessage());
							logger.error("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Wrong Payee Account Exception Occurred.");
						}
					}
					break;
				case 2:
					logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Adding new Payee.");
					Payee payee = new Payee();
					payee.setAccountId(accountHolder.getAccountId());
					System.out.println("Add Payee Account Number: ");
					payee.setPayeeAccountId(scanner.nextInt());
					System.out.println("Enter payee nick name");
					payee.setNickName(scanner.next());
					if (accountHolderService.addPayee(payee)) {
						System.out.println("Successfully Added");
						logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Payee Added Successfully.");
					}
					else {
						logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Payee Can't be Added.");
						System.out.println("Can not be added");
					}
					break;
				case 3:
					logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Going Back.");
					System.out.println("Going Back");
					break;
				case 4:
					System.out.println("Exiting from the application...");
					logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Exiting from application.");
					System.exit(0);
				default:
					logger.info("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Invalid Input.");
					System.err.println("Invalid Input... Please try again.");
				}
			} catch(InputMismatchException e) {
				System.err.print("Invalid Input(java.lang.InputMismatchException): Please Enter correct value. ");
				logger.error("Class: MainScreen -> Method: fundTransfer(AccountHolder) : Input Mismatch Exception Occurred.");
				scanner.next();
			}
		} while (choice != 3);
	}
}
