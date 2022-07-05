package runner;

import accountdeclare.Customer;
import inputcenter.InputCenter;
import manualexception.ManualException;
import zkartapi.ZKartAPI;

public class Runner {
	InputCenter input = new InputCenter();
	ZKartAPI api = new ZKartAPI();
	
	private static void menu()
	{
		System.out.println();
		System.out.println("1)SignUp Customer");
		System.out.println("2)Get Customer");
		System.out.println("3)Login");
		System.out.println();
	}
	
	
	private void signUpCustomer()
	{
		Customer customer = new Customer();
		
		customer.setUserName(input.getString("UserName/Email : "));
		
		customer.setPassword(input.getString("Password : "));
		
		customer.setName(input.getString("Name : "));
		
		customer.setMobile(input.getLong("Mobile Number : "));
		
		try
		{
			System.out.println(api.putCustomer(customer));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void getCustomer()
	{
		String userName = input.getString("UserName/Email : ");
		
		try {
			System.out.println(api.getCustomer(userName));
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void login()
	{
		String userName = input.getString("UserName/Email : ");
		
		try
		{
			System.out.println(api.login(userName));
			
			String password = input.getString("Password : ");
			
			System.out.println(api.login(userName,password));
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
	public static void main(String[] args)
	{
		InputCenter input = new InputCenter();
		Runner run = new Runner();
		run.load();
		
		boolean flag = true;
		while(flag)
		{
			menu();
			int process = input.getInt("");
			switch(process)
			{
			case 0:
				System.out.println("Runner Terminated");
				flag = false;
				break;
			case 1:
				run.signUpCustomer();
				break;
			case 2:
				run.getCustomer();
				break;
			case 3:
				run.login();
				break;
			default:
				System.out.println("Choose Wisely");
				break;
			}
		}
	}
}
