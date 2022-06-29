package runner;

import java.util.List;

import accountdeclare.FlightBasis;
import accountdeclare.Passenger;
import accountdeclare.Seating;
import flightsource.FlightSource;
import inputcenter.InputCenter;
import manualexception.ManualException;

public class Runner {

	private static void menu() {
		System.out.println();
		System.out.println("0) Terminate");
		System.out.println("1) Flight Source Set");
		System.out.println("2) Flight Source View");
		System.out.println("3) Load");
		System.out.println("4) Seatings Allot for Separate Slasses");
		System.out.println("5) Seatings Allot for Both Classes");
		System.out.println("6) View Flight BluePrint");
		System.out.println("7) Seat View");
		System.out.println("8) Flight Search via");
		System.out.println("9) Ticket Booking");
		System.out.println("10) Preferred Seat Search");
		System.out.println("11) DataBase Add");
		System.out.println("12) View Passenger");
		System.out.println("13) Meals Ordered Seat Locations");
		System.out.println("14) Cancellation");
		System.out.println();
	}

	InputCenter input = new InputCenter();

	FlightSource sourceObj = new FlightSource();
	
	private String seatPreference()
	{
		boolean flag = true;
		while(flag)
		{
		System.out.println();
		System.out.println("1) Window");
		System.out.println("2) Midlle");
		System.out.println("3) Aisle");
		System.out.println();
		
		int seatPreference = input.getInt("");
		
		switch(seatPreference)
		{
		case 0:
			flag = false;
			System.out.println("Seat Preference didn't fixed");
			break;
		case 1:
			return "Window";
		case 2:
			 return "Middle";
		case 3:
			return "Aisle";
		default:
			System.out.println("Invalid Seat Preference Selection");
		}
		}
		return "FootBoard";
	}
	
	private void flightSourceWrite() {

		try {

			int noOfFlights = input.getInt("No Of Flights : ");

			for (int i = 0; i < noOfFlights; i++) {

				String source = input.getString("Source : ");

				String destination = input.getString("Destination : ");

				sourceObj.flightSourceWrite(source, destination);

			}

		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}

	}

	private void flightSourceList() {
		try {
			System.out.println(sourceObj.flightDetails());
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}

	private void load() {
		try {
			sourceObj.load();
			System.out.println("Load Sucess");
		} catch (ManualException e) {

			System.out.println(e.getMessage());
		}
	}

	private void separateSeatings() {
		try {
			String flightNumber = input.getString("FlightNumber : ");

			System.out.println();
			System.out.println("1) Bussiness Class Alone");
			System.out.println("2) Economy Class Alone");
			System.out.println();

			int choice = input.getInt("");
			
			int noOfRows = 0;
			
			FlightBasis flightBasis = new FlightBasis();
			switch(choice)
			{
			case 1:
			{
			noOfRows = input.getInt("No of rows : ");
			
			flightBasis.setBusinessPrice(input.getInt("Surge Business Price : "));
			}
			break;
			
			case 2:
			{
			noOfRows = input.getInt("No of rows : ");
				
			flightBasis.setEconomyPrice(input.getInt("Surge Economy Price : " ));
			break;
			
			}
			}
			
			
			
			System.out.println(sourceObj.separateSeatings(flightNumber, choice, noOfRows,flightBasis));

		} catch (ManualException e) {

			System.out.println(e.getMessage());
		}
	}

	private void generalSeatings() {
		try {
			String flightNumber = input.getString("FlightNumber : ");

			int bussinessRows = input.getInt("Bussiness Rows : ");

			int economyRows = input.getInt("Economy Rows : ");
			
			FlightBasis flightBasis = new FlightBasis();
			
			flightBasis.setBusinessPrice(input.getInt("Surge Business Price : "));
			
			flightBasis.setEconomyPrice(input.getInt("Surge Economy Price : " ));

			sourceObj.generalSeatings(flightNumber, bussinessRows, economyRows,flightBasis);
			
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}

	private void viewBluePrint() {
		try {
			String flightNumber = input.getString("Flight Number : ");

			System.out.println(sourceObj.viewBluePrint(flightNumber));
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}

	private void flightSearch() {
		try {
			String source = input.getString("Source");
			String destination = input.getString("Destination");

			System.out.println(sourceObj.flightSearch(source, destination));
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}

	private void flightSearchClass() {
		try {
			
			List<String> list = sourceObj.flightSearch();
			
			if(!list.isEmpty())
			{
				System.out.println(list);
			}
			else
			{
				System.out.println("Oops!No Business Flights Found");
			}
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void preferredSeatSearch()
	{
		String flightNumber = input.getString("Flight Number : ");
		
		try
		{
			System.out.println(sourceObj.preferredSeatSearch(flightNumber, seatPreference()));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void addDataBase()
	{
			Passenger passenger = new Passenger();
			passenger.setName(input.getString("Passenger Name : "));
			passenger.setAddress(input.getString("Address : "));
			passenger.setMobile(input.getLong("Mobile : "));
			System.out.println(sourceObj.addDataBase(passenger));
	
	}
//	private void bussinessSeatAssign()
//	{
//		try
//		{
//			String flightNumber = input.getString("Flight Number : ");
//			
//			System.out.println(sourceObj.bussinessSeatAssign(flightNumber));
//		}
//		catch(ManualException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	private void economySeatAssign()
//	{
//		try
//		{
//			String flightNumber = input.getString("Flight Number : ");
//			
//			System.out.println(sourceObj.economySeatAssign(flightNumber));
//		}
//		catch(ManualException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	private void generalSeatAssign()
//	{
//		try
//		{
//			String flightNumber = input.getString("Flight Number : ");
//			
//			System.out.println(sourceObj.generalSeatAssign(flightNumber));
//		}
//		catch(ManualException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
	

	private void seater() {
		try {
			System.out.println(sourceObj.seatMap(input.getString("FlightNumber : ")));
		} catch (ManualException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private String seatClass()
	{
		boolean flag = true;
		
		while(flag)
		{
			System.out.println();
			System.out.println("1) Business Class");
			System.out.println("2) Economy Class");
			System.out.println();
			
			int seatClass = input.getInt("");
			
			switch(seatClass)
			{
			case 0:
				flag = false;
				break;
			case 1:
				flag = false;
				return "Business";
			case 2:
				flag = false;
				return "Economy";
			default:
				System.out.println("Invalid Class Selection");
			}
		}
		return "Ground Class";
	}
	
	private void ticketBook()
	{
		
		Seating seat = new Seating();
		try 
		{
		seat.setPassengerId(input.getInt("Passenger Id : "));
		
		System.out.println("Welcome "+sourceObj.passengerCheck(seat.getPassengerId()));
		
		seat.setSeatClass(seatClass());
		
		seat.setFlightNumber(input.getString("Flight Number : "));
		
		seat.setSource(input.getString("Source : "));
		
		seat.setDestination(input.getString("Destination : "));
		
		seat.setSeatPreference(seatPreference());
		
		System.out.println(sourceObj.preferredSeatSearch(seat.getFlightNumber(), seat.getSeatPreference()));
		
		seat.setSeatNo(input.getString("Seat No : "));
		
		boolean galf = true;
		while(galf)
		{
			System.out.println();
			System.out.println("1) Want Meal");
			System.out.println("2) No Meal");
			System.out.println();
			
			int mealPreference = input.getInt("");
			switch(mealPreference)
			{
			case 0:
				galf = false;
				System.out.println("Meal Preference didn't fixed");
				break;
			case 1:
				seat.setMeal(true);
				galf = false;
				break;
			case 2:
				seat.setMeal(false);
				galf = false;
				break;
			default:
				System.out.println("Invalid Meal Selection");
			}
		}
		System.out.println(sourceObj.ticketBooking(seat));
		
		System.out.println(sourceObj.payment(input.getInt("Amount : ")));
		
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
		}
	
	private void viewMealsOrdered()
	{
		try
		{
			System.out.println(sourceObj.mealOrders(input.getString("Flight Number : ")));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	private void viewPassenger()
	{
		try
		{
			System.out.println(sourceObj.viewPassenger(input.getInt("Booking Id : ")));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void cancelTicket()
	{
		try
		{
			System.out.println(sourceObj.ticketCancellation(input.getInt("Booking Id : ")));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		InputCenter input = new InputCenter();

		Runner run = new Runner();

		boolean flag = true;

		while (flag) {
			menu();
			int choice = input.getInt("Operation : ");
			switch (choice) {
			case 0:
				flag = false;
				System.out.println("Runner Terminated");
				break;
			case 1:
				run.flightSourceWrite();
				break;
			case 2:
				run.flightSourceList();
				break;
			case 3:
				run.load();
				break;
			case 4:
				run.separateSeatings();
				break;
			case 5:
				run.generalSeatings();
				break;
			case 6:
				run.viewBluePrint();
				break;
			case 7:
				run.seater();
				break;
			case 8:

				boolean galf = true;

				while (galf) {
					System.out.println();
					System.out.println("1) Source and Destination");
					System.out.println("2) Search for Business Flights");
					System.out.println();
					int via = input.getInt("");

					switch (via) {
					case 0:
						galf = false;
						System.out.println("Flight Search Terminated");
						break;
					case 1:
						run.flightSearch();
						break;
					case 2:
						run.flightSearchClass();
						break;
					default:
						System.out.println("Invalid Search");
						break;
					}
				}
				break;
			case 9:
				run.ticketBook();
				break;
			case 10:
				run.preferredSeatSearch();
				break;
			case 11:
				run.addDataBase();
				break;
			case 12:
				run.viewPassenger();
				break;
			case 13:
				run.viewMealsOrdered();
				break;
			case 14:
				run.cancelTicket();
				break;
			}
		}

	}
}
