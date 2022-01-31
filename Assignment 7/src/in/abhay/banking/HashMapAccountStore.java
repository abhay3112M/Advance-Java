package in.abhay.banking;

import java.util.ArrayList;
import java.util.HashMap;

import in.abhay.banking.exceptions.InvalidAccountException;

public class HashMapAccountStore {
	int lastAccountNumber=0;
	HashMap<Integer,BankAccount> accounts = new HashMap<Integer,BankAccount>();
	//BankAccount [] accounts=new BankAccount[100];
	

	public int addAccount(BankAccount account) {
		int accountNumber= ++lastAccountNumber;
		account.setAccountNumber(accountNumber);
		accounts.put(accountNumber,account);
		return accountNumber;
	}
	
	public BankAccount getAccount(int accountNumber) {
		if(accounts.containsKey(accountNumber))
			return accounts.get(accountNumber);
		else
			throw new InvalidAccountException(accountNumber);
	}

	public void removeAccount(int accountNumber) {
		if(accounts.containsKey(accountNumber))
			accounts.remove(accountNumber);
		else
			throw new InvalidAccountException(accountNumber);
	}
	
	public ArrayList<BankAccount> getAllActiveAccounts() {
		ArrayList<BankAccount> allAccounts= new ArrayList<>(accounts.values());
		return allAccounts;	
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return accounts.size();
	}
}
