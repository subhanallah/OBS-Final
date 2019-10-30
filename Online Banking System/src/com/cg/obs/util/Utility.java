package com.cg.obs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.cg.obs.ui.MainScreen;


/**
 * The class {@code Utility} will validate all the input parameters given by user..
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
public class Utility {

	private final static Logger logger = Logger.getLogger(Utility.class);

	
	public static boolean validateChoice(int choice) {
		logger.info("Class: Utility -> Method: validateChoice(int) : Validating Choice.");
		if (choice == 1 || choice == 2 || choice == 3)
			return true;
		return false;
	}
	
	public static boolean validateNoOfPages(int noOfPages) {
		logger.info("Class: Utility -> Method: validateNoOfPages(int) : Validating No of Pages.");
		if (noOfPages == 50 || noOfPages == 100 || noOfPages == 200)
			return true;
		return false;
	}
	
	public static boolean validateOpeningBalance(double openningBalance) {
		logger.info("Class: Utility -> Method: validateNoOfPages(int) : Validating opening balance.");
		if (openningBalance >= 0)
			return true;
		return false;
	}
	
	public static boolean validateName(String name) {
		logger.info("Class: Utility -> Method: validateName(String) : Validating Name.");
		Pattern pattern = Pattern.compile("[a-zA-Z ]{3,}");
		Matcher patternMatcher = pattern.matcher(name.trim());
		return (patternMatcher.find() && patternMatcher.group().equals(name.trim()));
	}
	
	/**
	 * Validates mobileNumber entered   
	 * 
	 * @param mobileNumber
	 * 			mobileNumber is a String passed from main entered by user as mobileNumber
	 *
	 * @return {@code true} if the mobile Number matches with the validation pattern {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */
	public static boolean validateMobileNumber(String mobileNumber) {
		logger.info("Class: Utility -> Method: validateMobileNumber(String) : Validating Mobile Number.");
		// The given argument to compile() method
		// is regular expression. With the help of
		// regular expression we can validate mobile
		// number.
		// 1) Begins with 0 or 91
		// 2) Then contains 7 or 8 or 9.
		// 3) Then contains 9 digits
		Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		// Pattern class contains matcher() method
		// to find matching between given number
		// and regular expression
		Matcher patternMatcher = pattern.matcher(mobileNumber);
		return (patternMatcher.find() && patternMatcher.group().equals(mobileNumber));

	}

	/**
	 * Validates emailId entered   
	 * 
	 * @param emailId
	 * 			emailId is a String passed from main entered by user as emailId
	 * 
	 * @return {@code true} if the email Id matches with the validation pattern {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */
	public static boolean validateEmailId(String emailId) {
		logger.info("Class: Utility -> Method: validateEmailId(String) : Validating Email Id.");
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (emailId == null)
			return false;
		return pat.matcher(emailId).matches();

	}

	/**
	 * Validates accountType entered   
	 * 
	 * @param accountType
	 * 			accountType is a String passed from main entered by user as accountType
	 * 
	 * @return {@code true} if the accountType matches with the validation pattern {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */
	public static boolean validateAccountType(String accountType) {
		logger.info("Class: Utility -> Method: validateAccountType(String) : Validating Account Type.");
		if(accountType.toLowerCase().equals("savings")|| accountType.toLowerCase().equals("current"))
			return true;
		else
			return false;
	}

	/**
	 * Validates panNumber entered   
	 * 
	 * @param panNumber
	 * 			panNumber is a String passed from main entered by user as panNumber
	 * 
	 * @return {@code true} if the panNumber matches with the validation pattern {@code boolean}
     *          equivalent to this boolean, {@code false} otherwise
	 * 			
	 * 	
	 */
	public static boolean validatePanNumber(String panNumber) {
		logger.info("Class: Utility -> Method: validatePanNumber(String) : Validating PanNumber.");
		/*
		 * The first three letters are sequence of alphabets from AAA to zzz The fourth
		 * character informs about the type of holder of the Card. Each assesses is
		 * unique:` C � Company P � Person H � HUF(Hindu Undivided Family) F � Firm A �
		 * Association of Persons (AOP) T � AOP (Trust) B � Body of Individuals (BOI) L
		 * � Local Authority J � Artificial Judicial Person G � Government The fifth
		 * character of the PAN is the first character (a) of the surname / last name of
		 * the person, in the case of a "Personal" PAN card, where the fourth character
		 * is "P" or (b) of the name of the Entity/ Trust/ Society/ Organization in the
		 * case of Company/ HUF/ Firm/ AOP/ BOI/ Local Authority/ Artificial Jurdical
		 * Person/ Government, where the fourth character is
		 * "C","H","F","A","T","B","L","J","G". 4) The last character is a alphabetic
		 * check digit.
		 */
		//String panNumberRegex = "^/[a-zA-Z]{3}[PCHFATBLJG]{1}[a-zA-Z]{1}[0-9]{4}[a-zA-Z]{1}/$";
		String panNumberRegex = "[A-Z]{3}[ABCFGHLJPTF]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}";
		Pattern pat = Pattern.compile(panNumberRegex);
		if (panNumber == null)
			return false;
		return pat.matcher(panNumber).matches();
	}
	
}
