package runner;

import inputcenter.InputCenter;

import manualexception.ManualException;

import java.util.concurrent.TimeUnit;

import accountdeclare.Account;
import api.AtmAPI;

public class Runner extends Thread{

	AtmAPI api = new AtmAPI();
	
	InputCenter input = new InputCenter();
	
	private static void menu()
	{
		System.out.println();
		System.out.println("1) Initialize ATM Balance");
		System.out.println("2) Add ATM Balance");
		System.out.println("3) View ATM Balance");
		System.out.println("4) Put Customer Detail");
		System.out.println("5) Read Customer General");
		System.out.println("6) Read Key");
		System.out.println("7) Tabular Customer Details");
		System.out.println("8) Login");
		System.out.println();
	}
	
	private static void atmProcess()
	{
		System.out.println();
		System.out.println("1) Check Balance");
		System.out.println("2) Deposit Money");
		System.out.println("3) Withdraw Money");
		System.out.println("4) Transfer Money");
		System.out.println("5) Check ATM Balance");
		System.out.println("6) Mini Statement");
		System.out.println();
	}
	
	private void customerDetail()
	{
		Account account = new Account();
		account.setAccountHolder(input.getString("Account Holder Name : "));
		account.setPinNumber(input.getInt("Account Pin No. "));
		account.setBalance(input.getInt("Balance : "));
		
		try
		{
		System.out.println(api.customerDetails(account));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void readCustomer()
	{
		try
		{
			System.out.println(api.readCustomerDetails());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void readKey() throws ManualException
	{
		try
		{
			System.out.println(api.readKey());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void load()
	{
		try
		{
			System.out.println(api.load());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void tabularCustomerDetails()
	{
		try
		{
			System.out.println(api.customerTable());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void ATMBalance()
	{
		try
		{
			String noOfTwos = input.getString("No.of Two Thousand's : ");
			
			String noOfFives = input.getString("No.Of Five Hundred's : ");
			
			String noOfHuns = input.getString("No.of Hundred's : ");
			
			System.out.println(api.initialATMBalance(noOfTwos, noOfFives, noOfHuns));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void addATMBalance()
	{
		try
		{
			int noOfTwos = input.getInt("No.of Two Thousand's : ");
			
			int noOfFives = input.getInt("No.Of Five Hundred's : ");
			
			int noOfHuns = input.getInt("No.of Hundred's : ");
			
			System.out.println("Updated ATM Balance\n");
			
			System.out.println(api.addATMBalance(noOfTwos, noOfFives, noOfHuns));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void viewATMBalance()
	{
		try
		{
			System.out.println(api.viewATMBalance());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void depositMoney(String fromAccNo, int noOfTwos, int noOfFiveHuns , int noOfHuns)
	{
		try 
		{
			System.out.println(api.depositMoney(fromAccNo, noOfTwos,noOfFiveHuns, noOfHuns));
			
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void withdrawMoney(String fromAccNo, int amount)
	{
		try
		{
			System.out.println(api.withdraw(fromAccNo, amount));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void transferMoney(String fromAccNo, String toAccNo , int amount)
	{
		try
		{
			System.out.println(api.transferMoney(fromAccNo, toAccNo, amount));
		} catch (ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void miniStatement(String fromAccNo)
	{
		try {
			System.out.println(api.miniStatement(fromAccNo));
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void handleATMProcess()
	{
//		try
//		{
		    String fromAccNo = input.getString("Account No : ");
		    
		    int pin = input.getInt("PIN : ");
		    
		    System.out.println(api.login(fromAccNo, pin));
		    
			if(api.login(fromAccNo, pin).contains("Welcome"))
			{
				
			boolean flag = true;
			
			while(flag)
			{
			atmProcess();
			int process = input.getInt("");
			
			switch(process)
			{
			case 0:
				flag = false;
				System.out.println("Don't forget to remove ur card\nThank You!");
				break;
			case 1:
				System.out.println(api.balance(fromAccNo));
				break;
			case 2:
			{
				int noOfTwos = input.getInt("No.of two thousand's : ");
				
				int noOfFiveHuns = input.getInt("No.of five hundred's : ");
				
				int noOfHuns = input.getInt("No.of hundred's : ");
				
				depositMoney(fromAccNo,noOfTwos, noOfFiveHuns, noOfHuns);
			}
			break;
			case 3:
			{
				int amount = input.getInt("Withdraw money must between ₹1000 and ₹10,000 : ");
				withdrawMoney(fromAccNo,amount);
			}
				break;
			case 4:
			{
				String toAccNo = input.getString("Enter To Account Number : ");
				int amount = input.getInt("Transfer money must between ₹1000 and ₹10,000 : ");
				
				transferMoney(fromAccNo,toAccNo,amount);
			}
				break;
			case 5:
				viewATMBalance();
				break;
			case 6:
				miniStatement(fromAccNo);
				break;
			default :
				System.out.println("Invalid Operation!");
			}
			}
			}
			
//		}
//		catch(ManualException e)
//		{
//			System.out.println(e.getMessage());
//		}
	}


	public static void main(String[] args) throws ManualException
	{
		InputCenter input = new InputCenter();
		Runner run = new Runner();
		
		run.load();
		
		
		boolean flag = true;
		
		while(flag)
		{
			menu();
			
			int choice = input.getInt("");
			
			switch(choice)
			{
			case 0:
				flag = false;
				System.out.println("Runner Terminated");
				break;
			case 1:
				run.ATMBalance();
				break;
			case 2:
				run.addATMBalance();
				break;
			case 3:
				run.viewATMBalance();
				break;
			case 4:
				run.customerDetail();
				break;
			case 5:
				run.readCustomer();
				break;
			case 6:
				run.readKey();
				break;
			case 7:
				run.tabularCustomerDetails();
				break;
			case 8:
				
				run.handleATMProcess();
				break;
			
			}
		}
	}
}
