package runner;

import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.Gender;
import accountdeclare.Points;
import api.CallTaxiAPI;
import inputcenter.InputCenter;
import manualexception.ManualException;

public class Runner {
	InputCenter input = new InputCenter();
	CallTaxiAPI api = new CallTaxiAPI();
	
	private static void gender()
	{
		System.out.println();
		System.out.println("1) Male");
		System.out.println("2) Female");
		System.out.println("3) Transgender");
		System.out.println();
	}
	
	private static void menu()
	{
		System.out.println();
		System.out.println("1)Put Customer Details");
		System.out.println("2)Get Customer Details");
		System.out.println("3)Car Fleet");
		System.out.println("4)Current Location Available Taxis");
		System.out.println();
	}
	
	private void startingPoint(Booking booking)
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println("1)A 2)B 3)C 4)D 5)E");
			int point = input.getInt("");
			switch(point)
			{
			case 0:
				flag = false;
				System.out.println("Point selection failed");
			case 1:
				booking.setStartingPoint(Points.A);
				flag = false;
				break;
			case 2:
				booking.setStartingPoint(Points.B);
				flag = false;
				break;
			case 3:
				booking.setStartingPoint(Points.C);
				flag = false;
				break;
			case 4:
				booking.setStartingPoint(Points.D);
				flag = false;
				break;
			case 5:
				booking.setStartingPoint(Points.E);
				flag = false;
				break;
			default:
				System.out.println("Invalid Starting Point");
				break;
			}
		}
	}
	
	private void destinationPoint(Booking booking)
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println("1)A 2)B 3)C 4)D 5)E");
			int point = input.getInt("");
			switch(point)
			{
			case 0:
				flag = false;
				System.out.println("Point selection failed");
			case 1:
				booking.setDestinationPoint(Points.A);
				flag = false;
				break;
			case 2:
				booking.setDestinationPoint(Points.B);
				flag = false;
				break;
			case 3:
				booking.setDestinationPoint(Points.C);
				flag = false;
				break;
			case 4:
				booking.setDestinationPoint(Points.D);
				flag = false;
				break;
			case 5:
				booking.setDestinationPoint(Points.E);
				flag = false;
				break;
			default:
				System.out.println("Invalid Starting Point");
				break;
			}
		}
	}
	private void load()
	{
		try {
			System.out.println(api.load());
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void putCustomer()
	{
		Customer customer = new Customer();
		String name = input.getString("Customer Name : ");
		String address = input.getString("Address : ");
		boolean flag = true;
		
		while(flag)
		{
			gender();
			int choice = input.getInt("");
			switch(choice)
			{
			case 0:
				flag = false;
				break;
			case 1:
				customer.setGender(Gender.MALE);
				flag = false;
				break;
			case 2:
				customer.setGender(Gender.FEMALE);
				flag = false;
				break;
			case 3:
				customer.setGender(Gender.TRANSGENDER);
				flag = false;
				break;
			default:
				System.out.println("Choose Wisely");
			}
		}	
		Long mobileNo = input.getLong("MobileNo : ");
		
		customer.setName(name);
		customer.setAddress(address);
		customer.setMobileNo(mobileNo);
	
		try {
			System.out.println(api.putCustomer(customer));
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void getCustomer()
	{
		String customerId = input.getString("Customer Id : ");
		try {
			System.out.println(api.getCustomer(customerId));
		} catch (ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void nFleet()
	{
		int noOfTaxis = input.getInt("Number of Taxis : ");
		try {
			System.out.println(api.nFleet(noOfTaxis));
		} 
		catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	private void booking()
	{
		Booking booking = new Booking();
		
		int customerId = input.getInt("CustomerId : ");
		startingPoint(booking);
		destinationPoint(booking);
		int distance = api.calculate(booking);
		if(distance>=45)
		{
			boolean flag = true;
			while(flag)
			{
				System.out.println("1)No Share\n2)Share");
				boolean noShare = true;
				boolean share = false;
				int value = input.getInt("");
				switch(value)
				{
				case 0:
					flag = false;
					System.out.println("Share selection failed");
					break;
				case 1:
					booking.setBookingType(noShare);
					booking.setCharges((short) (distance*10));
					flag = false;
					break;
				case 2:
					booking.setBookingType(share);
					booking.setCharges((short)(distance*10*0.4));
					flag = false;
					break;
				default:
					System.out.println("Select wisely!");
					break;
				}
			}
		} else
		{
			booking.setBookingType(true);
			booking.setCharges((short) (distance*10));
		}
		
	    
	}
	private void searchTaxis() throws ManualException
	{
		boolean flag = true;
		while(flag)
		{
			System.out.println("1)A 2)B 3)C 4)D 5)E");
			int choice = input.getInt("");
			switch(choice)
			{
			case 0:
				flag = false;
				System.out.println("Search Cancelled");
				break;
			case 1:
				System.out.println(api.searchTaxis("A"));
				break;
			case 2:
				System.out.println(api.searchTaxis("B"));
				break;
			case 3:
				System.out.println(api.searchTaxis("C"));
				break;
			case 4:
				System.out.println(api.searchTaxis("D"));
				break;
			case 5:
				System.out.println(api.searchTaxis("E"));
				break;
			default:
				System.out.println("Enter Wisely");
				break;
			}
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
				flag = false;
				System.out.println("Runner Terminated");
				break;
			case 1:
				run.putCustomer();
				break;
			case 2:
				run.getCustomer();
				break;
			case 3:
				run.nFleet();
				break;
			case 4:
				try {
					run.searchTaxis();
				} catch (ManualException e) 
				{
					System.out.println(e.getMessage());
				}
				break;
			default:
				System.out.println("Invalid Process");
				break;
			}	
		}
	}
	
}
