package com.bank.unittesting;
import org.junit.Test;
import com.bank.services.ResponseStatus;
import com.bank.services.Response;
import com.bank.account.BankAccount;
import static org.junit.Assert.*;

public class BankTest {
	final String correctPassword="pass@1";
	final int initialBalance=2200;
	
	@Test
	public void depositFailedDueToNegativeAmount() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.deposit(-1);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_AMOUNT);
	}
	
	@Test
	public void depositPassed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.deposit(200);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
	
	@Test
	public void withdrawFailedDueToInvalidCredential() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.withdraw("pass1",1800);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void withdrawFailedDueToNegativeAmount() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.withdraw("pass@1",-1);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_AMOUNT);
	}
	
	@Test
	public void withdrawFailedDueToInsufficientAmount() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.withdraw("pass@1",2300);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INSUFFICIENT_FUNDS);
	}
	
	@Test
	public void withdrawPassed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.withdraw("pass@1",1800);
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
	@Test
	public void changePasswordFailed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.changePassword("pass1","pass123");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void changePasswordPassed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.changePassword("pass@1","pass123");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
	@Test
	public void getBalanceFailed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.getBalanceCustomer("pass123");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void getBalancePassed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.getBalanceCustomer("pass@1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
	@Test
	public void getInfoFailed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.getInfo("pass123");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void getInfoPassed() {
		BankAccount b1=new BankAccount(234576, "Abhay",correctPassword, initialBalance);
		Response response = b1.getInfo("pass@1");
		ResponseStatus result = response.getCode();
		
		assertEquals(result,ResponseStatus.SUCCESS);
	}
	
}
