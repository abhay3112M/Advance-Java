package com.abhay.unittesting;
import org.junit.Test;

import com.abhay.banking.BankAccount;
import com.abhay.banking.Response;
import com.abhay.banking.ResponseStatus;

import static org.junit.Assert.*;

public class BankAccountTesting {
	//withdrawTest("Should fail for wrong password", a1, balance-1, "not_"+correctPassword, ResponseStatus.INVALID_CREDENTIALS);
	final String correctPassword="p@ss1";
	final int balance=22000;
	BankAccount b1=new BankAccount(1, "Abhay",correctPassword, balance,12);
	
	@Test
	public void depositFailedDueToNegativeAmount() {
		boolean result = b1.deposit(-1);
		
		assertEquals(result,false);
	}
	
	@Test
	public void depositPassed() {
		boolean result = b1.deposit(1);
		
		assertEquals(result,true);
	}
	
	
	@Test
	public void withdrawFailedDueToInvalidCredential() {
		Response response = b1.withdraw(18000,"pass1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void withdrawFailedDueToNegativeAmount() {
		Response response = b1.withdraw(-1,"p@ss1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_AMOUNT);
	}
	
	@Test
	public void withdrawFailedDueToInsufficientAmount() {
		Response response = b1.withdraw(23333,"p@ss1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INSUFFICIENT_FUNDS);
	}
	
	@Test
	public void withdrawPassed() {
		Response response = b1.withdraw(18000,"p@ss1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
	
}
