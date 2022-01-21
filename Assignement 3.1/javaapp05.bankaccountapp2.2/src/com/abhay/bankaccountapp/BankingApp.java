package com.abhay.bankaccountapp;

import com.abhay.banking.BankAccount;


public class BankingApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		BankAccount a1=new BankAccount(1, "Vivek", "p@ss#1", 20000, 12);
		System.out.println(a1.info());
	}

}
