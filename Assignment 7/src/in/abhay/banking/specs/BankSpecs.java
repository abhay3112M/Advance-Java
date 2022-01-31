package in.abhay.banking.specs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.Before;
import org.junit.Test;

import in.abhay.banking.Bank;
import in.abhay.banking.CurrentAccount;
import in.abhay.banking.OverdraftAccount;
import in.abhay.banking.SavingsAccount;
import in.abhay.banking.exceptions.InsufficientBalanceException;
import in.abhay.banking.exceptions.InvalidAccountException;
import in.abhay.banking.exceptions.InvalidAccountTypeException;
import in.abhay.banking.exceptions.InvalidAmountException;
import in.abhay.banking.exceptions.InvalidCredentialsException;

public class BankSpecs {
	
	final String bankName="ICICI";
	final double rate=12;
	final String correctPassword="p@ss";
	final double initialBalance=50000;
	
	int savingsAccountNumber, currentAccountNumber,odAccountNumber;
	int initialTotalAccounts;
	Bank bank;
	BankAsserts bankAsserts;
	
	@Before
	public void arrange() {
		//ARRANGE
		bank=new Bank(bankName,rate);
		savingsAccountNumber=bank.openAccount("savings","Name1", correctPassword, initialBalance);
		currentAccountNumber=bank.openAccount("current","Name", correctPassword, initialBalance);
		odAccountNumber=bank.openAccount("overdraft", "Name1", correctPassword,initialBalance);
		bankAsserts=new BankAsserts(bank, correctPassword, initialBalance);
		initialTotalAccounts=bank.getAccountCount();
	}
	
	
	@Test
	public void bankShouldHaveAName() {
				
		//ACT
		Bank bank=new Bank(bankName,10);
		
		//ASSERT
		assertEquals(bankName, bank.getName());
		
	}
	
	
	@Test
	public void bankShouldHaveAInterestRAte() {
		
		//ACT
		Bank bank=new Bank("Some Name",rate);
		
		//ASSERT
		assertEquals(rate, bank.getRate(),0);
					
	}
	

	@Test
	public void openAccountShouldTakeAccountTypeNamePasswordAndBalanceAndReturnAccountNumber() {
		int accountNumber1 = bank.openAccount("savings", "Aman", "mypassword", 1000.0);
		assertTrue(accountNumber1 > 0);
	}
	
	
	@Test		
	public void openAccountShouldReturnUniqueAccountNumber() {
		var accountNumber1 = bank.openAccount("savings","aman", "mypassword", 1000.0 );
		var accountNumber2 = bank.openAccount("savings","arpit", "hispassword", 2000.0 );
		assertNotEquals(accountNumber1, accountNumber2);
	}
	
	
	@Test
	public void openAccountShouldIncreaseTotalAccountCountInTheBank() {
		var accountNumber1 = bank.openAccount("savings","aman", "mypassword", 1000.0 );
		assertEquals(initialTotalAccounts+1, bank.getAccountCount());
	}
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateSavingsAccount() {	
		var accountNumber= bank.openAccount("savings", "Name1", correctPassword, initialBalance);		
		var account=bank.getAccount(accountNumber, correctPassword);		
		assertTrue("Account is Not Savings Account",account instanceof SavingsAccount);		
	}
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateCurrentAccount() {
		var accountNumber= bank.openAccount("current", "Name1", correctPassword, initialBalance);		
		var account=bank.getAccount(accountNumber, correctPassword);		
		assertTrue("Account is not current Account", account instanceof CurrentAccount);
	}
	
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateOverdraftAccount() {
		var accountNumber= bank.openAccount("overdraft", "Name1", correctPassword, initialBalance);
		var account=bank.getAccount(accountNumber, correctPassword);
		assertTrue( account instanceof OverdraftAccount);
	}
	
	
	
	
	@Test(expected = InvalidAccountTypeException.class)
	public void openAccountShouldFailForInvalidAccountType() {
		bank.openAccount("unknown type","Name",correctPassword,initialBalance);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void closeAccountShouldFailFromInvalidAccountNumber() {
		bank.closeAccount(initialTotalAccounts+1, "any-password");		
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void closeAccountShouldFailFromInvalidAccountPassword() {
		bank.closeAccount(savingsAccountNumber, "wrong-password");
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void closeAccountShouldFailForNegativeBalance() {
		bank.withdraw(odAccountNumber, initialBalance+100, correctPassword);
		bank.closeAccount(odAccountNumber, correctPassword);		
		
	}
	
	
	@Test
	public void closeAccountShouldCloseTheAccountWithRightCredentials() {
		var result = bank.closeAccount(savingsAccountNumber, correctPassword);
		assertEquals(initialBalance, result,0);
	}
	
	
	
	@Test
	public void closeAccountShouldReturnBalanceOnSuccessfulClosure() {
		var result= bank.closeAccount(savingsAccountNumber, correctPassword);
		assertEquals(initialBalance, result,0.01);
		
		
	}
	
	@Test
	public void closeAccountShouldReduceTheAccountCountInTheBank() {
		var result= bank.closeAccount(savingsAccountNumber, correctPassword);
		assertEquals(initialTotalAccounts-1, bank.getAccountCount());
	}
	
	@Test(expected = InvalidAccountException.class)
	public void closeShouldFailForAlreadyClosedAccount() {
		bank.closeAccount(savingsAccountNumber, correctPassword);
		bank.closeAccount(savingsAccountNumber, correctPassword);		
		
	}
	
	@Test
	public void accountNumberShouldNotBeReused() {
		String a4Name="Account "+(initialTotalAccounts+1);
		String a5Name="Account "+(initialTotalAccounts+2);
		bank.openAccount("savings",a4Name, correctPassword, initialBalance); //4
		bank.openAccount("savings",a5Name, correctPassword, initialBalance); //5
		
		bank.closeAccount(3, correctPassword); //we closed account 3
		var accountNumber= bank.openAccount("savings",a5Name, correctPassword, initialBalance);
		assertEquals(initialTotalAccounts+3,accountNumber);
		
		var account4= bank.getAccount(4,correctPassword);
		
		assertEquals(a4Name, account4.getName());	
	}
	
	@Test(expected = InvalidAccountException.class)
	public void weShouldNotBeAbleToGetClosedAccount() {
		var amount=bank.closeAccount(currentAccountNumber, correctPassword);
		assumeTrue(amount==initialBalance);
		bank.getAccount(currentAccountNumber, correctPassword);
	}
	
	
	@Test
	public void creditInterstShouldCreditInterstToSavingsAccounts() {
		bank.creditInterest();
		double expectedBalance= initialBalance*(1+ rate/1200);
		bankAsserts.assertBalance(savingsAccountNumber, expectedBalance);
	}
	
	@Test
	public void creditInterstShouldCreditInterstToOverdraftAccounts() {
		bank.creditInterest();
		double expectedBalance= initialBalance*(1+ rate/1200);		
		bankAsserts.assertBalance(odAccountNumber, expectedBalance);
	}
	
	@Test
	public void creditInterstShouldNotCreditInterstToCurrentAccounts() {
		bank.creditInterest();				
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
	}
	
	
	@Test(expected = InvalidAccountException.class)
	public void creditInterstShouldNotCreditInterstToClosedAccount() {
		
		bank.closeAccount(currentAccountNumber, correctPassword);
		bank.getAccount(currentAccountNumber, correctPassword);
		bank.creditInterest();				
	}
	
	
	@Test
	public void getBalanceShouldReturnBalanceForCorrectAccountAndPassword() {
		double balance=bank.getBalance(savingsAccountNumber, correctPassword);
		assertEquals(initialBalance,balance, 0.01);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void getBalanceShouldFailForNegativeAccountNumber() {
		bank.getBalance(-1, correctPassword);
	}
	
	
	@Test(expected = InvalidAccountException.class)
	public void getBalanceShouldFailForInvalidAccountNumber() {
		bank.getBalance(initialTotalAccounts+1, correctPassword);
				
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void getBalanceShouldFailForInvalidPassword() {
		bank.getBalance(savingsAccountNumber,"not"+ correctPassword);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void getBalanceShouldFailForClosedAccount() {
		bank.closeAccount(savingsAccountNumber, correctPassword);
		bank.getBalance(savingsAccountNumber, correctPassword);
	}
	
	
	
	@Test(expected = InvalidAccountException.class)
	public void depositShouldFailForInvalidAccountNumber() {
		bank.deposit(initialTotalAccounts+1, 20000);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void depositShouldFailForInvalidAmount() {
	
		bank.deposit(savingsAccountNumber, -1);
		
	}
	
	@Test
	public void depositShouldCreditBalanceOnSuccess() {
	
		bank.deposit(savingsAccountNumber, 100);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance+100);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void withdrawShouldFailForInvalidAccountNumber() {
		bank.withdraw(initialTotalAccounts+1, 1, correctPassword);
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void withdrawShouldFailForInvalidPassword() {
	
		bank.withdraw(savingsAccountNumber, 1, "not-"+correctPassword);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForInvalidAmount() {
		bank.withdraw(currentAccountNumber, -1, correctPassword);
	}
	
	
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForAmountExceedMinBalanceInSavingsAccount() {
		SavingsAccount account=(SavingsAccount) bank.getAccount(savingsAccountNumber, correctPassword);
		bank.withdraw(savingsAccountNumber, initialBalance-account.getMinBalance()+1, correctPassword);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForOverDraftForCurrentAccount() {
		var toWithdraw= initialBalance + 1;
		
		bank.withdraw(currentAccountNumber, toWithdraw, correctPassword);
		
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForAmountOverBalancePlusOdLimitForOdAccount() {
		var toWithdraw= initialBalance*1.1 + 1;
		
		bank.withdraw(odAccountNumber, toWithdraw, correctPassword);
	}
	
	
	
	@Test
	public void withdrawShouldReduceBalanceByAmountOnSuccess() {
		
		bank.withdraw(savingsAccountNumber, 100, correctPassword);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance-100);
		
	}
	
	
	@Test(expected = InvalidAccountException.class)
	public void transferShouldFailForInvalidSourceAccountNumber() {
		bank.transfer(initialTotalAccounts+1, 1, correctPassword, savingsAccountNumber);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void transferShouldFailForInvalidTargetAccountNumber() {
		bank.transfer(savingsAccountNumber, 1, correctPassword, initialTotalAccounts+1);	
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void transferShouldFailForInvalidPassword() {
		
		bank.transfer(currentAccountNumber, 1, "not-"+correctPassword, savingsAccountNumber);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void transferShouldFailForInvalidAmount() {
		
		bank.transfer(currentAccountNumber, -1, correctPassword, savingsAccountNumber);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void transferShouldFailForOverDraft() {
		bank.transfer(currentAccountNumber, initialBalance+1, correctPassword, savingsAccountNumber);
	}
	
	@Test
	public void transferShouldReduceBalanceInSourceAccountOnSuccess() {
		
		bank.transfer(currentAccountNumber, 100, correctPassword, savingsAccountNumber);		
		bankAsserts.assertBalance(currentAccountNumber,initialBalance-100);
	}
	
	@Test
	public void transferShouldIncreseBalanceInTargetAccountOnSuccess() {
		
		bank.transfer(currentAccountNumber, 100, correctPassword, savingsAccountNumber);		
		bankAsserts.assertBalance(savingsAccountNumber,initialBalance+100);
	}
	
	
	
	
	@Test
	public void transferShouldAllowOverDraftFromOverDraftAccount() {
		
		double od=1000;
		double amountTransferred=initialBalance+od;
		double finalBalance =  - od - od/100;
		
		bank.transfer(odAccountNumber, amountTransferred, correctPassword, savingsAccountNumber);
		bankAsserts.assertBalance(odAccountNumber, finalBalance);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance+amountTransferred);
		
	}

	@Test
	public void canChangePasswordWithValidCurrentPassword() {
		var newPassword="newPassword";
		bank.changePassword(savingsAccountNumber, correctPassword, newPassword);
		var balance=bank.getBalance(savingsAccountNumber, newPassword);
		assertEquals(initialBalance,balance,0);
		
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void changePasswordFailsForInvalidCurrentPassword() {
		String newPassword="new password";
		bank.changePassword(savingsAccountNumber, "not"+correctPassword, newPassword);
	}
	
	
	@Test (expected = InvalidAccountException.class)
	public void changePasswordFailsForInvalidAccountNumber() {
		String newPassword="new password";
		bank.changePassword(-1, "not"+correctPassword, newPassword);
	}
	
	@Test 
	public void getAllAccountInfoShouldGiveInfoOfAllActiveAccounts() {
		assertEquals(initialTotalAccounts,bank.getAllAccountsInfo().length);
		bank.closeAccount(currentAccountNumber, correctPassword);
		assertEquals(initialTotalAccounts-1,bank.getAllAccountsInfo().length);
	}
	
	
	 ///------------
	@Test(expected = InvalidAmountException.class)
	public void OpenAccountShouldFailForNegativeBalance() {
		bank.openAccount("savings","Name2", correctPassword, -1);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void depositShouldFailForNotMultipleOf100() {
		bank.deposit(savingsAccountNumber, 1);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForNotMultipleOf100() {
		bank.withdraw(savingsAccountNumber, 1,correctPassword);
	}
}
