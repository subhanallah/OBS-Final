package com.cg.obs.exception;


/**
 * The Class {@code NoTransactionsException} is a subclass of {@code Exception} and 
 * will be invoked when there are the account Holder has not done any transaction...
 *
 * @GroupNo : 7
 * @Author1: Subhana- 46001890
 * @Author2: Atul Anand- 46001684
 * @Author3: Atal Tiwari- 46002419
 * @Author4: Survagya Agarwal- 46002233
 * @Version- 1.0
 * @Date-30/10/2019
 * 
 * */

// will be invoked when there are the accountholder has not done any transaction...
public class NoTransactionsException extends Exception{
	public NoTransactionsException(String message) {
		super(message);
	}
	@Override
	public String getMessage() {
		return "NoTransactionException: "+super.getMessage()+"\n";
	}
}
  