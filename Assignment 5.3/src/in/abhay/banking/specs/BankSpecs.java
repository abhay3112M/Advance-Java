package in.abhay.banking.specs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.abhay.banking.Bank;
import in.abhay.banking.Response;
import in.abhay.banking.ResponseStatus;

public class BankSpecs {
	
	final String bankName="ICICI";
	final double rate=7;
	final String correctPassword="p@ss";
	final double initialBalance=50000;
	
	int existingAccount1, existingAccount2;
	int initialTotalAccounts;
	Bank bank;
	
	@Before
	public void arrange() {
		//ARRANGE
		bank=new Bank(bankName);
		existingAccount1=bank.openAccount("Name1", correctPassword, initialBalance);
		existingAccount2=bank.openAccount("Name", correctPassword, initialBalance);
		initialTotalAccounts=bank.getAccountCount();
	}
	
	
	@Test
	public void bankShouldHaveAName() {
				
		//ACT
		Bank bank=new Bank(bankName);
		
		//ASSERT
		assertEquals(bankName, bank.getName());
		
	}
	
	
//	@Test
//	public void bankShouldHaveAInterestRAte() {
//		
//		//ACT
//		Bank bank=new Bank("Some Name");
//		
//		//ASSERT
//		assertEquals(rate, bank.getRate(),0);		
//		
//	}
//	
	@Test
	public void openAccountShouldTakeNamePasswordAndBalanceAndReturnAccountNumber() {
		
		//ACT
		int accountNumber1 = bank.openAccount("Aman", "mypassword", 1000.0);
		
		// ASSERT
		assertTrue(accountNumber1 > 0);
	}
	
	
	@Test		
	public void openAccountShouldReturnUniqueAccountNumber() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("aman", "mypassword", 1000.0 );
		var accountNumber2 = bank.openAccount("arpit", "hispassword", 2000.0 );
		
		// ASSERT
		assertNotEquals(accountNumber1, accountNumber2);
	}
	
	
	@Test
	public void openAccountShouldIncreaseTotalAccountCountInTheBank() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("aman", "mypassword", 1000.0 );
		
		
		// ASSERT
		assertEquals(initialTotalAccounts+1, bank.getAccountCount());
	}
	
	
	
	@Test
	public void closeAccountShouldFailFromInvalidAccountNumber() {
		
		//ACT
		var result = bank.closeAccount(initialTotalAccounts+1, "any-password");
		
		assertEquals(-1, result,0);
		
		
	}
	
	@Test
	public void closeAccountShouldFailFromInvalidAccountPassword() {
		//ACT
		var result = bank.closeAccount(existingAccount1, "wrong-password");
		
		assertEquals(-1, result,0);
		
	}
	
	
	
	@Test
	public void closeAccountShouldCloseTheAccountWithRightCredentials() {
		//ACT
		var result = bank.closeAccount(existingAccount1, correctPassword);
		
		//ASSERT
		assertNotEquals(-1, result,0);
	}
	
	@Test
	public void closeAccountShouldReturnBalanceOnSuccessfulClosure() {
		
		//ACT
		var result= bank.closeAccount(existingAccount1, correctPassword);
		//ASSERT
		assertEquals(initialBalance, result,0.01);
		
		
	}
	
	@Test
	public void closeAccountShouldReduceTheAccountCountInTheBank() {
		//ACT
		var result= bank.closeAccount(existingAccount1, correctPassword);
		
		//ASSERT
		assertEquals(initialTotalAccounts-1, bank.getAccountCount());
	}
	
	@Test
	public void closeShouldFailForAlreadyClosedAccount() {
		
		//ARRANGE
		bank.closeAccount(existingAccount1, correctPassword);
		//Now existingAccount1 is closed. It can't be closed again
		
		//ACT
		var result=bank.closeAccount(existingAccount1, correctPassword);		
		
		//ASSERT
		assertEquals(-1, result,0);
		
	}
	
	@Test
	public void accountNumberShouldNotBeReused() {
		//ARRANGE
		String a4Name="Account 4";
		String a5Name="Account 5";
		bank.openAccount("Name", correctPassword, initialBalance); //3
		bank.openAccount(a4Name, correctPassword, initialBalance); //4
		
		bank.closeAccount(3, correctPassword); //we closed account 3
		
		//ACT
		
		var accountNumber= bank.openAccount(a5Name, correctPassword, initialBalance);
		
		
		//ASSERT
		assertEquals(5,accountNumber);
		
		var account4= bank.getAccount(4,correctPassword);
		
		assertEquals(a4Name, account4.getName());
	}
	
	
	
	@Test
	public void weShouldNotBeAbleToGetClosedAccount() {
		bank.closeAccount(existingAccount1, correctPassword);
		
		//ACT
		var result=bank.getAccount(existingAccount1, correctPassword);		
		
		//ASSERT
		assertTrue(result==null);
	}
	
	@Test
	public void creditInterstShouldCreditInterstToAllAccounts() {
		bank.creditInterest();
		double updatedBalance = (initialBalance+(initialBalance*rate/1200));
		double balance1 = bank.getBalance(existingAccount1, correctPassword);
		double balance2 = bank.getBalance(existingAccount2, correctPassword);
		assertEquals(balance1, updatedBalance,0.01);
		assertEquals(balance2, updatedBalance,0.01);
	}
	
	@Test
	public void getBalanceShouldReturnBalanceForCorrectAccountAndPassword() {
		double balance1 = bank.getBalance(existingAccount1, correctPassword);
		assertEquals(balance1, initialBalance,0.01);
	}

	
	@Test
	public void getBalanceShouldFailForInvalidAccountNumber() {
		double balance = bank.getBalance(initialTotalAccounts+1, correctPassword);
		assertTrue(balance==-1);
	}
	
	@Test
	public void getBalanceShouldFailForInvalidPassword() {
		double balance = bank.getBalance(existingAccount1, "Wrong-password");
		assertTrue(balance==-1);
	}

	
	@Test
	public void depositShouldFailForInvalidAccountNumber() {
		boolean response = bank.deposit(initialTotalAccounts+1,5000);
		assertFalse(response);
	}
	@Test
	public void depositShouldFailForInvalidAmount() {
		boolean response = bank.deposit(existingAccount1,-23);
		assertFalse(response);
	}
	
	@Test
	public void depositShouldCreditBalanceOnSuccess() {
		boolean response = bank.deposit(existingAccount1,500);
		assertTrue(response);
		
		double updatedBalance = initialBalance+500;
		double balance = bank.getBalance(existingAccount1, correctPassword);
		assertEquals(balance,updatedBalance,0.01);
	}
	
	@Test
	public void withdrawShouldFailForInvalidAccountNumber() {
		Response response = bank.withdraw(initialTotalAccounts+1, 500, correctPassword);
		assertTrue(response.getCode()==ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void withdrawShouldFailForInvalidPassword() {
		Response response = bank.withdraw(existingAccount1, 500, "WrongPassword");
		assertTrue(response.getCode()==ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void withdrawShouldFailForInvalidAmount() {
		Response response = bank.withdraw(existingAccount1, -21, correctPassword);
		assertTrue(response.getCode()==ResponseStatus.INVALID_AMOUNT);
	}

	@Test
	public void withdrawShouldFailForOverDraft() {
		Response response = bank.withdraw(existingAccount1,(int)initialBalance+10, correctPassword);
		assertTrue(response.getCode()==ResponseStatus.INSUFFICIENT_FUNDS);
		
	}
	
	@Test
	public void withdrawShouldReduceBalanceByAmountOnSuccess() {
		Response response = bank.withdraw(existingAccount1,13000, correctPassword);
		assertTrue(response.getCode()==ResponseStatus.SUCCESS);
		
		double reducedBalance = initialBalance-13000;
		double updatedBalance = bank.getBalance(existingAccount1, correctPassword);
		assertEquals(reducedBalance,updatedBalance,0.01);
		
	}
	
	@Test
	public void transferShouldFailForInvalidSourceAccountNumber() {
		Response response = bank.transfer(initialTotalAccounts+1,13000, correctPassword,existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void transferShouldFailForInvalidPassword() {
		Response response = bank.transfer(existingAccount1,13000, "WrongPassword",existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void transferShouldFailForInvalidAmount() {
		Response response = bank.transfer(existingAccount1,-9898, correctPassword,existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.INVALID_AMOUNT);
	}
	
	@Test
	public void transferShouldFailForOverDraft() {
		Response response = bank.transfer(existingAccount1,(int)initialBalance+1200, correctPassword,existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.INSUFFICIENT_FUNDS);
	}

	@Test
	public void transferShouldReduceBalanceInSourceAccountOnSuccess() {
		Response response = bank.transfer(existingAccount1,13000, correctPassword,existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.SUCCESS);
		
		double reducedBalance = initialBalance-13000;
		double updatedBalance = bank.getBalance(existingAccount1, correctPassword);
		assertEquals(reducedBalance,updatedBalance,0.01);
	}
	
	@Test
	public void transferShouldFailForInvalidTargetAccountNumber() {
		Response response = bank.transfer(existingAccount1,13000, correctPassword,initialTotalAccounts+1);
		assertTrue(response.getCode()==ResponseStatus.INVALID_CREDENTIALS);
	}
	
	@Test
	public void transferShouldAddBalanceInTargetOnSuccess() {
		Response response = bank.transfer(existingAccount1,13000, correctPassword,existingAccount2);
		assertTrue(response.getCode()==ResponseStatus.SUCCESS);
		
		double increasedBalance = initialBalance+13000;
		double updatedBalance = bank.getBalance(existingAccount2, correctPassword);
		assertEquals(increasedBalance,updatedBalance,0.01);
	}
	

}
