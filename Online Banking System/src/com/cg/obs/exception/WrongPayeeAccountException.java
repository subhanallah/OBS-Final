package com.cg.obs.exception;

/**
 * The Class {@code WrongPayeeAccountException} is a subclass of {@code Exception} and 
 * will be invoked when the user will try to initiate payment to an invalid account id...
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

//Will be invoked when the user will try to initiate payment to an invalid account id...
public class WrongPayeeAccountException extends Exception {
	public WrongPayeeAccountException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return "WrongPayeeAccountException: " + super.getMessage();
	}
}
  