package api;
import java.io.File;

import java.io.IOException;

import java.util.Formatter;

import java.util.Set;
import java.util.TreeSet;


import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Account;
import accountdeclare.Transaction;
import json.Json;
import manualexception.ManualException;

public class AtmAPI {
	
	private int accNo = 100;
	private int transactionNo = 1000;
	JSONObject accountMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	JSONObject bankATM = new JSONObject();
	JSONObject transactionMap = new JSONObject();
	Gson gson = new Gson();
	Json json = new Json();
	
	
	@SuppressWarnings("unchecked")
	private int accountNo() throws ManualException
	{
		keyMap.put("Account.No",String.valueOf(++accNo));
		json.jsonWrite(keyMap,"KeyMap.json");
		return accNo;
	}
	
	@SuppressWarnings("unchecked")
	private int transactionNo() throws ManualException
	{
		keyMap.put("Transaction.No" , String.valueOf(++transactionNo));
		json.jsonWrite(keyMap, "KeyMap.json");
		return transactionNo;
	}
	

//	private void createCustomerFile(int accNo) throws ManualException
//	{
//		try(FileWriter writer = new FileWriter(accNo+".txt",true);)
//		{
//			String data = (String)accountMap.get(accNo);
//			
//			Account account = gson.fromJson(data, Account.class);
//			
//			writer.write(account.getAccNo());
//			writer.write(account.getPinNumber());
//			writer.write(account.getAccountHolder());
//			writer.write(account.getBalance());
//		}
//		catch(IOException e)
//		{
//			throw new ManualException("File Writing Failed for "+accNo+".txt");
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public String customerDetails(Account account) throws ManualException
	{
		int accountNo = accountNo();
		
		account.setAccNo(accountNo);
		
		String data = gson.toJson(account);
		
		String accNo = String.valueOf(accountNo);
		
//		JSONObject tempMap = new JSONObject();
		
//		tempMap.put(accNo,data);
		
		accountMap.put(accNo, data);

		
		File file = new File(accountNo+"_transactions.json");
		
		try {
			if(file.createNewFile())
			{
				//return "File Created : "+file.getName();
			}
			else
			{
				//return "Already Created";
			}
		} 
		catch (IOException e) {
			throw new ManualException("File Creation Failed : "+accNo);
		}
	
		
		json.jsonWrite(accountMap, "CustomerDetails.json");
		
		return "\nAccount created for "+account.getAccountHolder();
	}
	
	public JSONObject readCustomerDetails() throws ManualException
	{
		return json.jsonRead("CustomerDetails.json");
	}
	
	public JSONObject readKey() throws ManualException
	{
		return json.jsonRead("KeyMap.json");
	}
	public String load() throws ManualException
	{
		
		keyMap =  readKey();
		
		if(keyMap!=null)
		{
			String data = (String) keyMap.get("Account.No");
			
			accNo = Integer.valueOf(data);
			
			accountMap = readCustomerDetails();
			
			if(accountMap !=null) {
				
				bankATM = readATMBalance();
				
				if(bankATM != null)
				{
					transactionMap = readTransaction();
					
					if(transactionMap!=null)
					{
						String data1 = (String) keyMap.get("Transaction.No");
						
						transactionNo = Integer.valueOf(data1);
						
						return "Key Loaded \nCustomerDetails Loaded\nATMBalance Loaded\nTransactions Loaded"; 
					}
				}
			}
		}
		return "Load Failed";
	}
	
	@SuppressWarnings("unchecked")
	public String initialATMBalance(String noOfTwos, String noOfFives, String noOfHuns) throws ManualException
	{
		bankATM.put("2000", noOfTwos);
		bankATM.put("500", noOfFives);
		bankATM.put("100", noOfHuns);
		json.jsonWrite(bankATM, "ATM.json");
		Formatter fmt = new Formatter();
		fmt.format("%s %15s %15s\n", "Denomination","Number","Value");
		int twoThousand = Integer.valueOf(noOfTwos)*2000;
		int fiveHundred = Integer.valueOf(noOfFives)*500;
		int hundred = Integer.valueOf(noOfHuns)*100;
		fmt.format("%s %20s %18s\n","2000",noOfTwos,twoThousand );
		fmt.format("%s %21s %18s\n", "500",noOfFives,fiveHundred);
		fmt.format("%s %21s %18s\n", "100",noOfHuns, hundred);
		int sum = twoThousand + fiveHundred + hundred;
		
		return fmt+"\n Total Amount Available in the ATM = "+sum+"₹";
	}
	
	public JSONObject readATMBalance() throws ManualException
	{
		return json.jsonRead("ATM.json");
	}
	
	public JSONObject readTransaction() throws ManualException
	{
		return json.jsonRead("Transactions.json");
	}
	
	public Formatter customerTable() throws ManualException {
		
		Formatter fmt = new Formatter();
		fmt.format("%s %15s %15s %15s\n","Acc No","Account Holder","Pin Number","Account Balance");
		for (int i = 101; i <= accNo; i++) {
			String data = (String) accountMap.get(String.valueOf(i));
			Account acc = gson.fromJson(data, Account.class);
			fmt.format("%s %15s %15s %15s\n", acc.getAccNo(), acc.getAccountHolder(), acc.getPinNumber(),acc.getBalance());
			}
		return fmt;
	}
	
	@SuppressWarnings("unchecked")
	public Formatter addATMBalance(int noOfTwos, int noOfFives, int noOfHuns) throws ManualException 
	{
	//	bankATM = readATMBalance();
		Formatter fmt = new Formatter();
		if(bankATM!=null)
		{
			int twoThousand = Integer.valueOf((String) bankATM.get("2000")) + noOfTwos;
			bankATM.put("2000",String.valueOf(twoThousand));
			twoThousand *= 2000;
			
			int fiveHundred = Integer.valueOf((String) bankATM.get("500")) + noOfFives;
			bankATM.put("500", String.valueOf(fiveHundred));
			fiveHundred *= 500;
			
			int hundred = Integer.valueOf((String) bankATM.get("100")) + noOfHuns;
			bankATM.put("100", String.valueOf(hundred));
			hundred *= 100;
			
			json.jsonWrite(bankATM, "ATM.json");
			fmt.format("%s %15s %15s\n", "Denomination","Number","Value");
			fmt.format("%s %20s %18s\n","2000",bankATM.get("2000"),twoThousand );
			fmt.format("%s %21s %18s\n", "500",bankATM.get("500"),fiveHundred);
			fmt.format("%s %21s %18s\n", "100",bankATM.get("100"), hundred);
		}
		return fmt;
	}
	
	@SuppressWarnings("unchecked")
	private void removeATMBalance(int noOfTwos, int noOfFiveHuns , int noOfHuns) throws ManualException
	{
		int twoThousand = Integer.valueOf((String) bankATM.get("2000")) - noOfTwos;
		if(twoThousand < 0)
		{
			throw new ManualException("Sorry Customer!\nCurrently ATM is out of ₹2000 denomination");
		}
		bankATM.put("2000",String.valueOf(twoThousand));
		
		int fiveHundred = Integer.valueOf((String) bankATM.get("500")) - noOfFiveHuns;
		if(fiveHundred < 0)
		{
			throw new ManualException("Sorry Customer!\nCurrently ATM is out of ₹500 denomination");
		}
		bankATM.put("500", String.valueOf(fiveHundred));
		
		int hundred = Integer.valueOf((String) bankATM.get("100")) - noOfHuns;
		if(hundred < 0)
		{
			throw new ManualException("Sorry Customer!\nCurrently ATM is out of ₹100 denomination");
		}
		bankATM.put("100", String.valueOf(hundred));
		json.jsonWrite(bankATM, "ATM.json");
	}
	
	public String viewATMBalance() throws ManualException 
	{
		//bankATM = readATMBalance();
		Formatter fmt = new Formatter();
		
		String two_s = (String) bankATM.get("2000");
		String fiveHun_s = (String) bankATM.get("500");
		String hun_s = (String) bankATM.get("100");
		
		int twoThousand = Integer.valueOf(two_s)*2000;
		int fiveHundred = Integer.valueOf(fiveHun_s)*500;
		int hundred = Integer.valueOf(hun_s)*100;
		int sum = twoThousand + fiveHundred + hundred;
		
		
		if(bankATM!=null)
		{
			fmt.format("%s %15s %15s\n", "Denomination","Number","Value");
			fmt.format("%s %20s %18s\n","2000",two_s,twoThousand );
			fmt.format("%s %21s %18s\n", "500",fiveHun_s,fiveHundred);
			fmt.format("%s %21s %18s\n", "100",hun_s, hundred);
		}
		
		return fmt+"\n Total Amount Available in the ATM = "+sum+"₹";
	}
	
	public String login(String accNo , int pin)
	{
		String data = (String) accountMap.get(accNo);
		
		if(data!=null)
		{
		Account account = gson.fromJson(data, Account.class);
		if(pin == account.getPinNumber())
		{
			return "Welcome "+account.getAccountHolder()+"!";
		}
		else
		{
			return "Password Incorrect!";
		}
		}
		return "Account Number Incorrect!";
	}
	
	private Account getAccount(String accNo)
	{
		String data = (String) accountMap.get(accNo);
		
		Account account = gson.fromJson(data, Account.class);
		
		return account;
	}
	
	public String balance(String accNo)
	{
		Account account = getAccount(accNo);
		
		return "Your Balance : "+account.getBalance();
	}
	
	@SuppressWarnings("unchecked")
	public String depositMoney(String fromAccNo,int noOfTwos, int noOfFiveHuns, int noOfHuns) throws ManualException
	{
		Account account = getAccount(fromAccNo);
		
		int sum = 0;
		
		if(account!=null)
		{
			int balance = account.getBalance();
			
			int twoThousand = noOfTwos*2000;
			
			int fiveHundred = noOfFiveHuns*500;
			
			int hundred = noOfHuns*100;
			
			sum = twoThousand+fiveHundred+hundred;

			addATMBalance(noOfTwos, noOfFiveHuns, noOfHuns);
			
			account.setBalance(balance+sum);
			
			String data = gson.toJson(account);
			
			accountMap.put(fromAccNo, data);
			
			json.jsonWrite(accountMap, "CustomerDetails.json");
			
			Transaction transaction = new Transaction();
			
			transaction.setTransactionNo(transactionNo());
			
			transaction.setAmount(sum);
			
			transaction.setDescription("Cash Deposit");
			
			transaction.setTransferType("Credit");
			
			transaction.setClosingBalance(account.getBalance());
			
			String transactionData = gson.toJson(transaction);
			
			String transactionNo = String.valueOf(transaction.getTransactionNo());
			
			transactionMap.put(transactionNo, transactionData);
			
			File fromFile = new File(fromAccNo+"_transactions.json");
			
			JSONObject tempFrom = null ;
			
			if(fromFile.length() == 0L)
			{
				tempFrom = new JSONObject();
			}
			else
			{
				tempFrom = json.jsonRead(fromAccNo+"_transactions.json");	
			}
			
			tempFrom.put(transactionNo, transactionData);
			
			json.jsonWrite(tempFrom, fromAccNo+"_transactions.json");
			
			json.jsonWrite(transactionMap, "Transactions.json");
		
		}
		else
		{
			throw new ManualException("Invalid Account Number");
		}
		
		return "₹"+sum+" deposited successfully";
	}
	
	@SuppressWarnings("unchecked")
	public String withdraw(String fromAccNo, int amount) throws ManualException 
	{
		Account account = getAccount(fromAccNo);
		
		if(amount <1000)
		{
			return "Transaction money should be more than ₹1000";
		}
		if(amount > 10000)
		{
			return "Transaction shouldn't exceed ₹10000";
		}
		int balance = 0;
		
		if(account!=null)
		{
			 balance = account.getBalance()-amount;
			
			if(balance >0)
			{
				switch(amount)
				{
				case 3000:
					removeATMBalance(0,3,15);
					break;
				case 2000:
					removeATMBalance(0,1,15);
					break;
				case 1500:
					removeATMBalance(0,1,10);
				case 1000:
					removeATMBalance(0,0,10);
					break;
				}
			}
			else
			{
				return "Insufficient Balance to Withdraw";
			}
		}
		account.setBalance(balance);
		
		String data = gson.toJson(account);
		
		accountMap.put(fromAccNo, data);
		
		Transaction transaction = new Transaction();
		
		transaction.setTransactionNo(transactionNo());
		
		transaction.setAmount(amount);
		
		transaction.setDescription("Cash Withdrawal");
		
		transaction.setTransferType("Debit");
		
		transaction.setClosingBalance(balance);
		
		String transactionData = gson.toJson(transaction);
		
		String transNo = String.valueOf(transactionNo);
		
		transactionMap.put(transNo, transactionData);
		
		File fromFile = new File(fromAccNo+"_transactions.json");
		
		JSONObject tempFrom = null ;
		
		if(fromFile.length() == 0L)
		{
			tempFrom = new JSONObject();
		}
		else
		{
			tempFrom = json.jsonRead(fromAccNo+"_transactions.json");	
		}
		
		tempFrom.put(transNo, transactionData);
		
		json.jsonWrite(tempFrom, fromAccNo+"_transactions.json");
		
		json.jsonWrite(transactionMap, "Transactions.json");
		
		return "Withdrawal of ₹"+amount+" success";
	}
	
	@SuppressWarnings("unchecked")
	public String transferMoney(String fromAccNo,String toAccNo, int amount) throws ManualException
	{
		if(fromAccNo.equals(toAccNo))
		{
			return "Amount shouldn't transferred to same account";
		}
		if(amount <1000)
		{
			return "Transaction money should be more than ₹1000";
		}
		if(amount > 10000)
		{
			return "Transaction shouldn't exceed ₹10000";
		}
		
		Account fromAccount = getAccount(fromAccNo);
		
		int fromBalance = fromAccount.getBalance()-amount;
		
		if(fromBalance < 0)
		{
			return "Insufficient Balance "+fromBalance;
		}
		Transaction fromTransaction = new Transaction();
		
		fromTransaction.setTransactionNo(transactionNo());
		
		fromTransaction.setDescription("Transfer to "+toAccNo);
		
		fromTransaction.setTransferType("Debit");
		
		fromTransaction.setAmount(amount);
		
		fromTransaction.setClosingBalance(fromBalance);
		
		fromAccount.setBalance(fromBalance);
		
		Account toAccount = getAccount(toAccNo);
		
		Transaction toTransaction = new Transaction();
		
		if(toAccount!=null)
		{
		int toBalance = toAccount.getBalance()+amount;
		
		toTransaction.setTransactionNo(transactionNo());
		
		toTransaction.setDescription("Transfer from "+fromAccNo);
		
		toTransaction.setTransferType("Credit");
		
		toTransaction.setAmount(amount);
		
		toTransaction.setClosingBalance(toBalance);
		
		toAccount.setBalance(toBalance);
		
		}
		else
		{
			return "Account Number Not Found "+toAccNo;
		}
		
		String fromTransactionData = gson.toJson(fromTransaction);
		
		String toTransactionData = gson.toJson(toTransaction);
		
		String fromTransactionNo = String.valueOf(fromTransaction.getTransactionNo());
		
		String toTransactionNo = String.valueOf(toTransaction.getTransactionNo());
		
		transactionMap.put(fromTransactionNo,fromTransactionData);
		
		transactionMap.put(toTransactionNo, toTransactionData);
		
		
		File fromFile = new File(fromAccNo+"_transactions.json");
		
		JSONObject tempFrom = null ;
		
		if(fromFile.length() == 0L)
		{
			tempFrom = new JSONObject();
		}
		else
		{
			tempFrom = json.jsonRead(fromAccNo+"_transactions.json");
		}
		
//		try {
//			
//			TimeUnit.MILLISECONDS.sleep(5000);
//		} catch (InterruptedException e) 
//		{
//			throw new ManualException("Time Delay Failed");
//		}
		
		tempFrom.put(fromTransactionNo, fromTransactionData);

		json.jsonWrite(tempFrom, fromAccNo+"_transactions.json");
		
		JSONObject tempTo = null;
		
		File toFile = new File(toAccNo+"_transactions.json");
		
		if(toFile.length() == 0L)
		{
			tempTo = new JSONObject();	
		}
		else
		{
			tempTo = json.jsonRead(toAccNo+"_transactions.json");
		}
		
//		try {
//		
//			TimeUnit.MILLISECONDS.sleep(5000);
//		} catch (InterruptedException e) 
//		{
//			throw new ManualException("Time Delay Failed");
//		}
//		
		tempTo.put(toTransactionNo, toTransactionData);
		
		json.jsonWrite(tempTo, toAccNo+"_transactions.json");
		
		json.jsonWrite(transactionMap, "Transactions.json");
		
		String fromData = gson.toJson(fromAccount);
		
		String toData = gson.toJson(toAccount);
		
		accountMap.put(fromAccNo, fromData);
		
		accountMap.put(toAccNo, toData);
		
		json.jsonWrite(accountMap, "CustomerDetails.json");
		
		return "Money Transferred to "+toAccount.getAccountHolder()+" account successfully!";
	}
	
	public Formatter miniStatement(String accNo) throws ManualException
	{
		JSONObject jsonObj =  json.jsonRead(accNo+"_transactions.json");
		
		Formatter fmt = new Formatter();
		
		int count = 0;
		
		if(jsonObj!=null)
		{
			@SuppressWarnings("unchecked")
			Set<String> set = jsonObj.keySet();
			
			TreeSet<String> treeSet = new TreeSet<>(set);
			
			TreeSet<String> descendingSet = (TreeSet<String>) treeSet.descendingSet();
			
			fmt.format("%s %15s %25s %10s %18s\n","Transaction Number","Description","Credit/Debit","Amount","Closing Balance");
			
			for(String name : descendingSet)
			{
				count++;
				
				String data = (String) jsonObj.get(name);
				
				Transaction transaction = gson.fromJson(data, Transaction.class);
				
				fmt.format("%s %33s %15s %15s %15s\n", transaction.getTransactionNo(),transaction.getDescription(),
						transaction.getTransferType(),transaction.getAmount(),transaction.getClosingBalance());
				
				if(count==10)
				{
					break;
				}
			}
	
			return fmt;
		}
		throw new ManualException("No Transaction Files");
	}
	
	

}
