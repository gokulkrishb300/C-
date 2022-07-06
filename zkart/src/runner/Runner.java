package runner;

import accountdeclare.Category;
import accountdeclare.Customer;
import accountdeclare.Inventory;
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
		System.out.println("2)View Customer's");
		System.out.println("3)Get Customer");
		System.out.println("4)Login");
		System.out.println("5)Intialize Inventory");
		System.out.println("6)Add Stocks");
		System.out.println("7)View Inventory");
		System.out.println();
	}
	
	private static void category()
	{
		System.out.println();
		System.out.println("1)Laptop");
		System.out.println("2)Mobile");
		System.out.println("3)Tablet");
		System.out.println();
	}
	
	private static void prompt()
	{
		System.out.println();
		System.out.println("1) Create New Account at Z-Kart");
		System.out.println("2) Not Now");
		System.out.println();
	}
	
	private static void cart()
	{
		System.out.println();
		System.out.println("1) Wish to add on cart");
		System.out.println("2) Later");
		System.out.println();
	}
	
	private void categorySelect(Inventory inventory)
	{
		boolean flag = true;
		
		while(flag)
		{
			category();
			int select = input.getInt("");
			
			switch(select)
			{
			case 0:
				System.out.println("Category selection failed");
				flag = false;
				break;
			case 1:
				inventory.setCategory(Category.LAPTOP);
				flag = false;
				break;
			case 2:
				inventory.setCategory(Category.MOBILE);
				flag = false;
				break;
			case 3:
				inventory.setCategory(Category.TABLET);
				flag = false;
				break;
			default:
				System.out.println("Select Wisley");
				break;
			}
		}
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
			api.getCustomer(userName);
		
			System.out.println(api.getCustomerFormat(userName));
			
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void shopping(String userName)
	{
		boolean flag = true;
		
		while(flag)
		{
			cart();
			int choice = input.getInt("");
			switch(choice)
			{
			case 1:
				String mobile = input.getString("Category : ");
				String brand = input.getString("Brand : ");
				String model = input.getString("Model : ");
				try {
				System.out.println(api.addingCart(userName, mobile+"-"+brand+"-"+model));
					
				} catch (ManualException e)
				{
					System.out.println(e.getMessage());
				}
			case 2:
				flag = false;
				break;
			}
		}
	}
	private void login()
	{
		String userName = input.getString("UserName/Email : ");
		
		try
		{
			String temp = api.login(userName);
			
			if(!temp.equals(""))
			{
				System.out.println(temp);
				
				String password = input.getString("Password : ");
				
				System.out.println(api.login(userName,password));
				
				shopping(userName);
			}
			else
			{
			boolean flag = true;
			while(flag)
			{
				prompt();
				int choice = input.getInt("");
				switch(choice)
				{
				case 1:
					signUpCustomer();
					flag = false;
					break;
				case 2:
					System.out.println("See you soon!");
					flag = false;
					break;
			    default:
			    	System.out.println("Select wisely");
			    	break;
				}
			}
		  }
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void initializeInventory()
	{
		Inventory inventory = new Inventory();
		
		categorySelect(inventory);
		
		inventory.setBrand(input.getString("Brand : "));
		
		inventory.setModel(input.getString("Model : "));
		
		inventory.setPrice(input.getInt("Price : "));
		
		inventory.setStock((short)input.getInt("Stock : "));
		
		inventory.setDiscount(input.getFloat("Discount : "));
		
		try
		{
			System.out.println(api.initializeInventory(inventory));
			
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void addInventory()
	{
		Inventory inventory = new Inventory();
		
		categorySelect(inventory);
		
		inventory.setBrand(input.getString("Brand : "));
		
		inventory.setModel(input.getString("Model : "));
		
		inventory.setPrice(input.getInt("Price : "));
		
		inventory.setStock((short)input.getInt("Stock : "));
		
		inventory.setDiscount(input.getFloat("Discount : "));
		
		try
		{
			System.out.println(api.addStocks(inventory));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void viewCustomer()
	{
		System.out.println(api.customerFormat());
	}
	
	private void viewInventory()
	{
		System.out.println(api.inventoryFormat());
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
				run.viewCustomer();
				break;
			case 3:
				run.getCustomer();
				break;
			case 4:
				run.login();
				break;
			case 5:
				run.initializeInventory();
				break;
			case 6:
				run.addInventory();
				break;
			case 7:
				run.viewInventory();
				break;
			default:
				System.out.println("Choose Wisely");
				break;
			}
		}
	}
}
