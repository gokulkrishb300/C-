package runner;

import accountdeclare.ExecutiveActivity;
import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.Gender;
import accountdeclare.Points;
import fooddeliveryapi.FoodDeliveryAPI;
import inputcenter.InputCenter;
import manualexception.ManualException;

public class Runner {
	InputCenter input = new InputCenter();
	FoodDeliveryAPI api = new FoodDeliveryAPI();
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
		System.out.println("3)Intialize Delivery Executive");
		System.out.println("4)Read Delivery Executive");
		System.out.println("5)Get BookedId Details");
		System.out.println("6)Order Booking");
		System.out.println("7)Total Earned");
		System.out.println("8)Delivery History");
		System.out.println("9)Order Free");
		System.out.println();
	}
	
	private void putCustomer()												//PutCustomer
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
	
	private void getCustomer()											//GetCustomer	
	{
		String customerId = input.getString("Customer Id : ");
		try {
			System.out.println(api.getCustomer(customerId));
		} catch (ManualException e)
		{
			System.out.println(e.getMessage());
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
	
	private void getBookedId()
	{
		String bookedId = input.getString("Booked-Id : ");
		
		try
		{
			System.out.println(api.getBookedDetails(bookedId));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void nFleet()													//Initialize Fleet
	{
		int noOfDE = input.getInt("Number of Delivery Executives : ");
		try {
			System.out.println(api.nFleet(noOfDE));
		} 
		catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void readFleet()													//Read Fleet
	{
		try
		{
		System.out.println(api.afterBooking());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	private void restaurant(Booking booking,ExecutiveActivity travelHistory)				//Starting Point
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println("Restuarant\n1)A 2)B 3)C 4)D 5)E");
			int point = input.getInt("");
			switch(point)
			{
			case 0:
				flag = false;
				System.out.println("Restuarant selection failed");
			case 1:
				booking.setRestaurant(Points.A);
			    travelHistory.setRestaurant((Points.A));

				flag = false;
				break;
			case 2:
				booking.setRestaurant(Points.B);
			    travelHistory.setRestaurant((Points.B));
				flag = false;
				break;
			case 3:
				booking.setRestaurant(Points.C);
			    travelHistory.setRestaurant((Points.C));

				flag = false;
				break;
			case 4:
				booking.setRestaurant(Points.D);
			    travelHistory.setRestaurant((Points.D));

				flag = false;
				break;
			case 5:
				booking.setRestaurant(Points.E);
			    travelHistory.setRestaurant((Points.E));

				flag = false;
				break;
			default:
				System.out.println("Invalid Restuarant");
				break;
			}
		}
	}
	
	private void destinationPoint(Booking booking,ExecutiveActivity travelHistory)			//DestinationPoint
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println("Destination Point\n1)A 2)B 3)C 4)D 5)E");
			int point = input.getInt("");
			switch(point)
			{
			case 0:
				flag = false;
				System.out.println("Destiation selection failed");
			case 1:
				booking.setDestinationPoint(Points.A);
				travelHistory.setDestinationPoint(Points.A);
				flag = false;
				break;
			case 2:
				booking.setDestinationPoint(Points.B);
				travelHistory.setDestinationPoint(Points.B);

				flag = false;
				break;
			case 3:
				booking.setDestinationPoint(Points.C);
				travelHistory.setDestinationPoint(Points.C);

				flag = false;
				break;
			case 4:
				booking.setDestinationPoint(Points.D);
				travelHistory.setDestinationPoint(Points.D);

				flag = false;
				break;
			case 5:
				booking.setDestinationPoint(Points.E);
				travelHistory.setDestinationPoint(Points.E);

				flag = false;
				break;
			default:
				System.out.println("Invalid Destination Point");
				break;
			}
		}
	}
	private void orderBooking()
	{
		Booking booking = new Booking();
		
		ExecutiveActivity executive = new ExecutiveActivity();
		
		int customerId = input.getInt("Customer ID : ");
		
		try
		{
			
		restaurant(booking,executive);
		
		destinationPoint(booking,executive);
		
		api.getCustomer(customerId+"");
		
		booking.setCustomerId(customerId);
		
		String time = input.getString("Time : ");
		
		System.out.println(api.beforeBooking());
		
		String temp = api.validTime(time,booking,executive);
		
		if(temp.equals(""))
		{
		
		booking.setTime(time);
	
		System.out.println(api.deliveryBooking(booking, executive));
		}
		else
		{
			System.out.println(temp);
		}
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void deliveryHistory()
	{
		try
		{
			System.out.println(api.deliveryHistory());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void totalEarned()
	{
		try
		{
			System.out.println(api.afterBooking());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void orderFree()
	{
		try
		{
			String deNo = input.getString("Delivery Executive No. ");
			System.out.println(api.reduceOrder(deNo));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args)					//MAIN
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
				run.readFleet();
				break;
			case 5:
				run.getBookedId();
				break;
			case 6:
				run.orderBooking();
				break;
			case 7:
				run.totalEarned();
				break;
			case 8:
				run.deliveryHistory();
				break;
			case 9:
				run.orderFree();
				break;
			default:
				System.out.println("Invalid Process");
				break;
			}	
		}
	}

	
}
