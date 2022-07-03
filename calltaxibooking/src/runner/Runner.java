package runner;

import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.Gender;
import accountdeclare.Points;
import accountdeclare.TravelHistory;
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
		System.out.println("3)Intialize Car Fleet");
		System.out.println("4)Read Car Fleet");
		System.out.println("5)SearchTaxis");
		System.out.println("6)Taxi Booking");
		System.out.println("7)Get Taxi TravelHistory");
		System.out.println("8)Get BookedId Details");
		System.out.println();
	}
	
	private void startingPoint(Booking booking,TravelHistory travelHistory)				//Starting Point
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println("Starting Point\n1)A 2)B 3)C 4)D 5)E");
			int point = input.getInt("");
			switch(point)
			{
			case 0:
				flag = false;
				System.out.println("Point selection failed");
			case 1:
				booking.setStartingPoint(Points.A);
			    travelHistory.setStartingPoint(Points.A);

				flag = false;
				break;
			case 2:
				booking.setStartingPoint(Points.B);
				travelHistory.setStartingPoint(Points.B);

				flag = false;
				break;
			case 3:
				booking.setStartingPoint(Points.C);
				travelHistory.setStartingPoint(Points.C);

				flag = false;
				break;
			case 4:
				booking.setStartingPoint(Points.D);
				travelHistory.setStartingPoint(Points.D);

				flag = false;
				break;
			case 5:
				booking.setStartingPoint(Points.E);
				travelHistory.setStartingPoint(Points.E);

				flag = false;
				break;
			default:
				System.out.println("Invalid Starting Point");
				break;
			}
		}
	}
	
	private void destinationPoint(Booking booking,TravelHistory travelHistory)			//DestinationPoint
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
				System.out.println("Point selection failed");
			case 1:
				booking.setDestinationPoint(Points.A);
				travelHistory.setEndPoint(Points.A);
				flag = false;
				break;
			case 2:
				booking.setDestinationPoint(Points.B);
				travelHistory.setEndPoint(Points.B);

				flag = false;
				break;
			case 3:
				booking.setDestinationPoint(Points.C);
				travelHistory.setEndPoint(Points.C);

				flag = false;
				break;
			case 4:
				booking.setDestinationPoint(Points.D);
				travelHistory.setEndPoint(Points.D);

				flag = false;
				break;
			case 5:
				booking.setDestinationPoint(Points.E);
				travelHistory.setEndPoint(Points.E);

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
	
	private void putCustomer()					//PutCustomer
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
	
	private void getCustomer()						//GetCustomer	
	{
		String customerId = input.getString("Customer Id : ");
		try {
			System.out.println(api.getCustomer(customerId));
		} catch (ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void nFleet()								//Initialize Fleet
	{
		int noOfTaxis = input.getInt("Number of Taxis : ");
		try {
			System.out.println(api.nFleet(noOfTaxis));
		} 
		catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void readFleet()							//Read Fleet
	{
		try
		{
			System.out.println(api.readFleet());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	private void booking()									//Booking
	{
		Booking booking = new Booking();
		TravelHistory travelHistory = new TravelHistory();
		
		int customerId = input.getInt("CustomerId : ");
		booking.setCustomerId(customerId);
		travelHistory.setCustomerId(customerId);
		startingPoint(booking,travelHistory);
		destinationPoint(booking,travelHistory);
		String time = input.getString("Time : ");
		try {
			api.validTime(time);
		
		booking.setTime(time);
		travelHistory.setStartTime(time);
		int distance = api.calculate(booking);
		
		if(distance>=45)
		{
			boolean flag = true;
			while(flag)
			{
				boolean noShare = true;
				boolean share = false;
				int value = input.getInt("1)No Share\n2)Share\n");
				switch(value)
				{
				case 0:
					flag = false;
					System.out.println("Share selection failed");
					break;
				case 1:
				{
					booking.setBookingType(noShare);
					travelHistory.setBookingType(noShare);
					short temp = (short)(distance*10);
					booking.setCharges(temp);
					travelHistory.setCharges(temp);
					flag = false;
					break;
				}
				case 2:
				{
					booking.setBookingType(share);
					travelHistory.setBookingType(share);
					short temp = (short)(distance*10*0.4);
					booking.setCharges(temp);
					travelHistory.setCharges(temp);
					flag = false;
					break;
				}
				default:
					System.out.println("Select wisely!");
					break;
				}
			}
		} else
		{
			booking.setBookingType(true);
			booking.setCharges((short) (distance*10));
			travelHistory.setCharges((short)(distance*10));
			travelHistory.setBookingType(true);
		}

			System.out.println(api.ticketBooking(booking,travelHistory));
		}
		catch(ManualException e)
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
	
	private void getTravelHistory()								//GetTravelHistory
	{
		String taxiName = input.getString("Taxi-Name : ");
		
		try {
			System.out.println(api.getTravelHistory(taxiName));
		} catch (ManualException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	private void searchTaxis() throws ManualException		//searchTaxi
	{
		boolean flag = true;
		while(flag)
		{
			int choice = input.getInt("1)A 2)B 3)C 4)D 5)E\n");
			switch(choice)
			{
			case 0:
				flag = false;
				System.out.println("Search Cancelled");
				break;
			case 1:
				System.out.println("Taxi-"+api.allotedTaxi("A"));
				break;
			case 2:
				System.out.println("Taxi-"+api.allotedTaxi("B"));
				break;
			case 3:
				System.out.println("Taxi-"+api.allotedTaxi("C"));
				break;
			case 4:
				System.out.println("Taxi-"+api.allotedTaxi("D"));
				break;
			case 5:
				System.out.println("Taxi-"+api.allotedTaxi("E"));
				break;
			default:
				System.out.println("Enter Wisely");
				break;
			}
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
				try
				{
				run.searchTaxis();
				} catch (ManualException e) 
				{
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				run.booking();
				break;
			case 7:
				run.getTravelHistory();
				break;
			case 8:
				run.getBookedId();
				break;
			default:
				System.out.println("Invalid Process");
				break;
			}	
		}
	}
	
}
