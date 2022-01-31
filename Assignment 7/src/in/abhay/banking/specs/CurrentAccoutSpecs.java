package in.abhay.banking.specs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.abhay.banking.BankAccount;
import in.abhay.banking.CurrentAccount;
import in.abhay.banking.ResponseStatus;
import in.abhay.banking.exceptions.InvalidCredentialsException;

public class CurrentAccoutSpecs {
	String name="Name";
	double balance=50000;
	String correctPassword="p@ss";
	double interestRate=12;
	CurrentAccount account;

	@Before
	public void setUp() throws Exception {
		account=new CurrentAccount(1,name,correctPassword,balance);
	}

	
	@Test
	public void currentAccountIsATypeOfBankAccount() {
		assertTrue(account instanceof BankAccount);
	}
	
	
	@Test
	public void creditInterstDoesntCreditInterest() {
		account.creditInterest(interestRate);
		assertEquals(balance, account.getBalance(),0);
	}
	

	
	
	@Test
	public void withdrawCanWithdrawEntireBlance() {
		
		account.withdraw(balance, correctPassword);
		assertEquals(0, account.getBalance(),0);
	}
	
	
	@Test(expected = InvalidCredentialsException.class)
	public void withdrawFailsForWrongPassword() {
		
		account.withdraw(1, "not-"+correctPassword);
		
//		assertEquals(ResponseStatus.INVALID_CREDENTIALS,result.getCode());
//		assertEquals(balance, account.getBalance(),0);
		
	}
	
	
	

}
