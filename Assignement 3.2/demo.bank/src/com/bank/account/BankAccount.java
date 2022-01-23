package com.bank.account;
import com.bank.services.*;
import com.bank.utils.Encrypt;

public class BankAccount {
	private String name; //account holder name
	private int accountNumber;
	private String password;
	private double balance;
	
	public BankAccount(int accountNumber, String name,String password, double amount) {
		this.accountNumber=accountNumber;
		this.name=name;
		setPassword(password);
		this.balance=amount;  
	}
	
	public Response deposit(double amount) {
		if(amount>0) {
			this.balance+=amount;
			return new Response(ResponseStatus.SUCCESS,"Successfully Deposited");
		}else 
			return new Response(ResponseStatus.INVALID_AMOUNT,"Enter Positive Amount");		
	}
	
	public Response withdraw(String pwd,double amount) {
		if(!this.authenticate(pwd))
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		if(amount<0)
			return new Response(ResponseStatus.INVALID_AMOUNT,"Enter Positive Amount");
		if(amount>this.balance)
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Insufficient Funds");
		this.balance-=amount;
		return new Response(ResponseStatus.SUCCESS,"Please Collect Your Cash..!!!");
	}
	
	public Response changePassword(String oldPassword,String newPassword) {
		if(this.authenticate(oldPassword)) {
			setPassword(newPassword);
			return new Response(ResponseStatus.SUCCESS,"Password Successfully updated");
		} else {
			return new Response(ResponseStatus.INVALID_CREDENTIALS, "Wrong Current Password.");
		}
	}
	
	private void setPassword(String password) {
		this.password =Encrypt.instance.encrypt( password);
	}
	
	private boolean authenticate(String password) {
		return Encrypt.instance.match(this.password, password);
	}
	
	public Response getBalanceCustomer(String password) {
		if(this.authenticate(password))	{
			return new Response(ResponseStatus.SUCCESS,""+this.balance);
		}else {
			return new Response(ResponseStatus.INVALID_CREDENTIALS, "Wrong Password.");
		}
	}
	
	public int getAccountNumber() {return accountNumber;}
	
	public double getBalanceBank() {return this.balance;}
	
	public Response getInfo(String password) {
		//System.out.println(accountNumber+"\t"+name+"\t"+balance);
		if(this.authenticate(password)) {
			return new Response(ResponseStatus.SUCCESS,String.format("%d\t%s\t%f\n",accountNumber,name,balance));
		}
		else {
			return new Response(ResponseStatus.INVALID_CREDENTIALS, "Wrong Current Password.");
		}
	}
}


