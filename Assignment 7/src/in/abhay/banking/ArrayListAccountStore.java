package in.abhay.banking;

import java.util.ArrayList;

import in.abhay.banking.exceptions.InvalidAccountException;

public class ArrayListAccountStore {
	int lastAccountNumber=0;
	ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
	//BankAccount [] accounts=new BankAccount[100];
	

	public int addAccount(BankAccount account) {
		int accountNumber= ++lastAccountNumber;
		account.setAccountNumber(accountNumber);
		accounts.add(account);
		return accountNumber;
	}
	
	public BankAccount getAccount(int accountNumber) {
		int index = findAccountIndex(accountNumber);
		return accounts.get(index);
	}

	public void removeAccount(int accountNumber) {
		int index = findAccountIndex(accountNumber);
		accounts.remove(index);
	}
	
	public ArrayList<BankAccount> getAllActiveAccounts() {
		return accounts;	
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return accounts.size();
	}
	
	private int findAccountIndex(int accountNumber) {
		for(int i=0;i<accounts.size();i++){
			int currAccNum = accounts.get(i).getAccountNumber();
			if(currAccNum==accountNumber) {
				return i;
			}
		}
		throw new InvalidAccountException(accountNumber);
	}
}
