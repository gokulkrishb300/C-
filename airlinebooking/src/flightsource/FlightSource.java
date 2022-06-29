package flightsource;

import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import accountdeclare.FlightBasis;
import accountdeclare.Passenger;
import accountdeclare.Seat;
import accountdeclare.Seating;
import manualexception.ManualException;

public class FlightSource {

//	List<String> flightSource = new ArrayList<>();

	Map<String, Map<String, Seat>> flightSeater = new TreeMap<>();
	
	Map<String, FlightBasis> flightBasis = new HashMap<>();
	
	Map<Integer,Passenger> database = new HashMap<>();
	
	Map<Integer,Seating> bookingDetails = new HashMap<>();

	
	private int flightNumber = 112;
	
	private int bookingId = 1000;
	
	private int passengerId = 1;

	private int businessBasePrice = 2000;
	
	private int economyBasePrice = 1000;
	
	public String addDataBase(Passenger passenger)
	{
		passenger.setPassengerId(passengerId++);
		
		database.put(passengerId, passenger);
		
		return "Passenger Created";
	}
	

	public String passengerCheck(int passengerId) throws ManualException
	{
		if(database.get(passengerId)==null)
		{
			throw new ManualException("Passport Error");
		}
		return database.get(passengerId).getName();
	}
	
	private String flightModel() throws ManualException {
		flightNumberWrite();

		return "Flight-A" + (flightNumber++);
	}

	private void sourceCheck(String source, String destination) throws ManualException {
		if (source.equalsIgnoreCase(destination)) {
			throw new ManualException("Source and Destination should't be same");
		}

	}

	public void flightSourceWrite(String source, String destination) throws ManualException {
		
		sourceCheck(source, destination);

		StringBuilder build = new StringBuilder();

		build.append(flightModel());

		File file1 = new File("Ticket-Booking-" + build.toString() + ".txt");

		build.append("-" + source + "-" + destination);

		//flightSource.add(build.toString());

		File file = new File(build.toString() + ".txt");

		try {
			if (file.createNewFile() && file1.createNewFile()) {
				System.out.println("File Created : " + file.getName() + "\n" + file1.getName());
			} else {
				System.out.println("Already Exists");
			}
		} catch (IOException io) {
			throw new ManualException("File Creation Failed");
		}

		try (FileWriter writer = new FileWriter("FlightSource.txt", true);) {

			writer.write(build.toString() + "\n");

		} catch (IOException e) {

			throw new ManualException("File Writing Failed");
		}

	}

	public String flightDetails() throws ManualException {
		
		StringBuilder build = new StringBuilder();
		int count = 0;
		try (FileReader reader = new FileReader("FlightSource.txt");) {
			int i;
			while ((i = reader.read()) != -1) {
				if(i==10)
				{
					count++;
				}
				build.append((char) i);
			}
		} catch (IOException io) {
			throw new ManualException("Flight Source Failed to Read");
		}

		return "No.of Flights : "+count+"\n"+build.toString();
	}

	private void flightNumberWrite() throws ManualException {
		try (FileWriter writer = new FileWriter("FlightNumber.txt", false);) {
			writer.write(String.valueOf(flightNumber));
		} catch (IOException io) {
			throw new ManualException("Flight Number Writing Failed");
		}
	}

	public void load() throws ManualException {
		try (FileReader reader = new FileReader("FlightNumber.txt");) {
			int i;
			StringBuilder build = new StringBuilder();

			while ((i = reader.read()) != -1) {
				build.append((char) i);
			}

			flightNumber = Integer.valueOf(build.toString()) + 1;
			
			Passenger passenger = new Passenger();
			passenger.setPassengerId(passengerId);
			passenger.setName("Senthil");
			passenger.setAddress("Madurai");
			passenger.setMobile(1234567891);
			database.put(passengerId++, passenger);

		} catch (IOException io) {
			throw new ManualException("Flight Number Reading Failed");
		}
	}

	private String fileName(String flightNumber) throws ManualException {
		File dir = new File("/home/gokul-inc1502/eclipse-workspace/airlinebooking/");

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(flightNumber);
			}
		};
		String[] children = dir.list(filter);

		if (children == null) {
			throw new ManualException("Either dir does not exist or is not a directory");
		}

		String fileName = "";

		for (int i = 0; i < children.length; i++) {
			fileName = children[i];
		}

		return fileName;

	}

	public String separateSeatings(String flightNumber, int choice, int noOfRows,FlightBasis surgePrice) throws ManualException {

		String flightName = fileName("Flight-" + flightNumber);

		String ticketBooking = fileName("Ticket-Booking-Flight-" + flightNumber);
		
		switch (choice) {
		case 1:
			businessClass(noOfRows, flightName, ticketBooking);
			flightBasis.put(flightNumber, surgePrice);
			businessSeatAssign(flightNumber);
			break;

		case 2:
			economyClass(noOfRows, flightName, ticketBooking);
			flightBasis.put(flightNumber, surgePrice);
			economySeatAssign(flightNumber);
			break;

		default:
			throw new ManualException("Invalid Selection");
		}

		return flightName + "\nSeatings Alloted for Flight-" + flightNumber;

	}

	private void businessClass(int noOfRows, String flightName, String ticketBooking) throws ManualException {
		try (FileWriter writer = new FileWriter(flightName);) {
			try (FileWriter writer1 = new FileWriter(ticketBooking);) {
				writer.write("Business : {2,3,2}, " + noOfRows + "\n");

				for (int i = 0; i < noOfRows; i++) {
					writer.write("WA AMA AW\n");
					writer1.write("WA AMA AW\n");
				}
			}
		} catch (IOException io) {
			throw new ManualException("File Writing Failed for bussinessClass");
		}
	}

	private void economyClass(int noOfRows, String flightName, String ticketBooking) throws ManualException {
		try (FileWriter writer = new FileWriter(flightName);) {
			try (FileWriter writer1 = new FileWriter(ticketBooking);) {
				writer.write("Economy : {3,4,4}, " + noOfRows + "\n");

				for (int i = 0; i < noOfRows; i++) {
					writer.write("WMA AMMA AMMW\n");
					writer1.write("WMA AMMA AMMW\n");
				}

			}
		} catch (IOException io) {
			throw new ManualException("File Writing Failed for economyClass");
		}
	}

	public void generalSeatings(String flightNumber, int bussinessRows, int economyRows, FlightBasis surgePrice) throws ManualException {
		String flightName = fileName("Flight-" + flightNumber);

		String ticketBooking = fileName("Ticket-Booking-Flight-" + flightNumber);

		
		try (FileWriter writer = new FileWriter(flightName);) {
			try (FileWriter writer1 = new FileWriter(ticketBooking);) {
				writer.write("Business : {2,3,2}, " + bussinessRows + "\n");

				writer.write("Economy : {3,4,4}, " + economyRows + "\n");

				for (int i = 0; i < bussinessRows; i++) {
					writer.write("WA AMA AW\n");
					writer1.write("WA AMA AW\n");
				}

				for (int i = 0; i < economyRows; i++) {
					writer.write("WMA AMMA AMMW\n");
					writer1.write("WMA AMMA AMMW\n");
				}

			}
			flightBasis.put(flightNumber, surgePrice);
			
		} catch (IOException io) {
			throw new ManualException("File Writing Failed for general");
		}
		generalSeatAssign(flightNumber);
	}

	public String viewBluePrint(String flightNumber) throws ManualException {
		String fileName = fileName("Flight-" + flightNumber);

		StringBuilder build = new StringBuilder();

		try (FileReader reader = new FileReader(fileName);) {
			int i;

			while ((i = reader.read()) != -1) {
				build.append((char) i);
			}
		} catch (IOException io) {
			throw new ManualException("File Reading Failed");
		}

		return build.toString();
	}

	private int getFlightRows(String flightNumber) throws ManualException {
		String flightName = fileName("Flight-" + flightNumber);

		StringBuilder build = new StringBuilder();

		int i;

		try (FileReader reader = new FileReader(flightName);) {
			while ((i = reader.read()) != 10) {
				build.append((char) i);
			}
		} catch (IOException io) {
			throw new ManualException("Assigning seats raised a problem");
		}

		String value = build.toString().replaceAll("[a-zA-Z]", "").replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll("\\,", "").replaceAll("\\:", "").replaceAll("\\ ", "");

		return Integer.valueOf(value.substring(3));
	}

	public Map<String, Map<String, Seat>> businessSeatAssign(String flightNumber) throws ManualException {
		
		int row = getFlightRows(flightNumber);

		String[] position = { "Window", "Aisle", "Aisle", "Middle", "Aisle", "Aisle", "Window" };

		Map<String, Seat> temp = new LinkedHashMap<>();

		for (int j = 1; j <= row; j++) {
			int letter = 65;
			for (int k = 0; k < 7; k++) {
				Seat seat = new Seat();
				seat.setSeatPosition(position[k]);
				seat.setSeatNo(j+""+(char)letter++);
				seat.setSeatClass("Business");
				temp.put(seat.getSeatNo(), seat);
			}

		}
		flightSeater.put(flightNumber, temp);

		return flightSeater;

	}

	public Map<String, Map<String, Seat>> economySeatAssign(String flightNumber) throws ManualException {
		int row = getFlightRows(flightNumber);

		System.out.println("Economy Row " + row);

		String[] position = { "Window", "Middle", "Aisle", "Aisle", "Middle", "Middle", "Aisle", "Aisle", "Middle",
				"Middle", "Window" };

		Map<String, Seat> temp = new LinkedHashMap<>();

		for (int j = 1; j <= row; j++) {
			int letter = 65;
			for (int k = 0; k < 11; k++) {
				Seat seat = new Seat();
				seat.setSeatPosition(position[k]);
				seat.setSeatNo(j+""+(char)letter++);
				seat.setSeatClass("Economy");
				temp.put(seat.getSeatNo(), seat);
			}

		}
		flightSeater.put(flightNumber, temp);

		return flightSeater;
	}

	public Map<String, Map<String, Seat>> generalSeatAssign(String flightNumber) throws ManualException {

		String flightName = fileName("Flight-" + flightNumber);

		StringBuilder build1 = new StringBuilder();

		StringBuilder build2 = new StringBuilder();

		int i;

		try (FileReader reader = new FileReader(flightName);) {
			while ((i = reader.read()) != 10) {
				build1.append((char) i);
			}
			try (FileReader reader1 = new FileReader(flightName);) {
				while ((i = reader.read()) != 10) {
					build2.append((char) i);
				}
			}
		} catch (IOException io) {
			throw new ManualException("Assigning seats raised a problem in "+flightName);
		}

		String business = build1.toString().replaceAll("[a-zA-Z]", "").replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll("\\,", "").replaceAll("\\:", "").replaceAll("\\ ", "");

		int businessRows = Integer.valueOf(business.substring(3));

		String economy = build2.toString().replaceAll("[a-zA-Z]", "").replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll("\\,", "").replaceAll("\\:", "").replaceAll("\\ ", "");

		int economyRows = Integer.valueOf(economy.substring(3));

		String[] position = { "Window", "Aisle", "Aisle", "Middle", "Aisle", "Aisle", "Window" };

		Map<String, Seat> temp = new LinkedHashMap<>();

		for (int j = 1; j <= businessRows; j++) {
			int letter = 65;
			for (int k = 0; k < 7; k++) {
				Seat seat = new Seat();
				seat.setSeatPosition(position[k]);
				seat.setSeatNo(j+""+(char)letter++);
				seat.setSeatClass("Business");
				temp.put(seat.getSeatNo(), seat);
			}

		}

		String[] position1 = { "Window", "Middle", "Aisle", "Aisle", "Middle", "Middle", "Aisle", "Aisle", "Middle",
				"Middle", "Window" };

		for (int j = businessRows + 1; j <= economyRows + businessRows; j++) {
			int letter = 65;
			for (int k = 0; k < 11; k++) {
				Seat seat = new Seat();
				seat.setSeatPosition(position1[k]);
				seat.setSeatNo(j+""+(char)letter++);
				seat.setSeatClass("Economy");
				temp.put(seat.getSeatNo(), seat);
			}
		}
		flightSeater.put(flightNumber, temp);

		return flightSeater;
	}

	public Map<String, Seat> seatMap(String flightNumber) throws ManualException {
		if (flightSeater.get(flightNumber) == null) {
			throw new ManualException("Seats didn't allocated");
		}
		return flightSeater.get(flightNumber);
	}

	public List<String> flightSearch(String source, String destination) throws ManualException {
		
		File dir = new File("/home/gokul-inc1502/eclipse-workspace/airlinebooking/");

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(source + "-" + destination);
			}
		};
		String[] children = dir.list(filter);

		if (children == null) {
			throw new ManualException("Not in a directory");
		}

		String fileName = "";

		List<String> list = new ArrayList<>();

		for (int i = 0; i < children.length; i++) {
			fileName = children[i];
			list.add(fileName.replaceAll(destination, "").replaceAll(source, "").replaceAll("\\-", "")
					.replaceAll("\\.", "").replaceAll("txt", ""));
		}

		return list;
	}

	public List<String> flightSearch() throws ManualException
	{
		List<String> list = new ArrayList<>();
		try(FileReader reader = new FileReader("FlightSource.txt");)
		{
			int i ;

			StringBuilder build = new StringBuilder();
			while ((i = reader.read()) != -1) {
				if (i == 10) {
					list.add(build.toString());
					build.setLength(0);
					continue;
				}
				build.append((char) i);

			}
		}
		catch(IOException io)
		{
			throw new ManualException("File Reading Failed");
		}
		
		StringBuilder build = new StringBuilder();
		
		List<String> list1 = new ArrayList<>();
		
		for(int i = 0 ; i < list.size() ;i++ )
		{
			int j = 0;
			try(FileReader reader = new FileReader(list.get(i)+".txt");)
			{
				
				while((j=reader.read())!=-1)
				{
					if(j==69 ||(j == 10 && j+1 == 69))
					{ 
						build.setLength(0);
						break;
					}
					
					build.append((char)j);
					if(j==87)
					{
						if(build.toString().contains("Business"))
						{
							list1.add(list.get(i));
							break;
						}
					}
				}
			
			}
			catch(IOException io)
			{
				throw new ManualException("File Reading Failed ");
			}
		}
		return list1;
	}
	
	public String preferredSeatSearch(String flightNumber,String seatPreference) throws ManualException
	{
		Map<String,Seat> map = flightSeater.get(flightNumber);
		
		int count = 0;
		
		if(map==null)
		{
			throw new ManualException("Invalid Flight Number");
		}
		
		List<String> list = new ArrayList<>();
		
		for(String seatNo : map.keySet())
		{
			Seat seat = map.get(seatNo);

			if(!seat.getSeatStatus() && seat.getSeatPosition().equals(seatPreference))
			{
				count++;
				list.add(seat.getSeatNo());
			}
		}
		if(count == 0)
		{
			return "Oops! No "+seatPreference+" available";
		}
		
		return "Available Seats : "+count+"\n"+list;
	}
	
	int tempPrice = 0;
	int tempBookingId = 0;
	
	public String ticketBooking(Seating seating) throws ManualException
	{
//		List<String> mealList = new ArrayList<>();
		int price = 0;
		
		seating.setBookingId(bookingId++);
	
		FlightBasis surgePrice;
		try
		{
		surgePrice = flightBasis.get(seating.getFlightNumber());
		}
		catch(Exception e)
		{
			throw new ManualException("Wrong Flight Data Entered");
		}		
		Seat seat ;
		try
		{
		seat = flightSeater.get(seating.getFlightNumber()).get(seating.getSeatNo());
		}
		catch(Exception e)
		{
			throw new ManualException("Wrong Seat Data Entered");
		}
		
		if(seating.getSeatClass().equals("Business")||seating.getSeatClass().equals("Aisle"))
		{
			price += 100;
		}
//		String seatClass  = seat.getSeatClass(); 
		
		seat.setSeatStatus(true);
		
		if(seating.isMeal())
		{
			
			price += 200;

			seat.setMeal(true);
			
		}
		
		if(seating.getSeatClass().equals("Business"))
		{
			price += businessBasePrice;
			if(surgePrice.isBusinessInit())
			{
				price += surgePrice.getBusinessPrice();
				surgePrice.setBusinessPrice(surgePrice.getBusinessPrice());
			}
			surgePrice.setBusinessInit(true);
		}
		else
		{
		price += economyBasePrice;
		   if(surgePrice.isEconomyInit())
		   {
			   price += surgePrice.getEconomyPrice();
			   surgePrice.setEconomyPrice(surgePrice.getEconomyPrice());
		   }
		   surgePrice.setEconomyInit(true);
		}
		
		seating.setPrice(price);
		
		tempBookingId = seating.getBookingId();
		
		bookingDetails.put(tempBookingId, seating);
	
		tempPrice = seating.getPrice();
		
		return "Your Ticket Price : "+tempPrice;
	}
	
	public String payment(int amount)
	{
		boolean flag = true;
		
		while(flag)
		{
			int result = amount-tempPrice;
			switch(result)
			{
			case 0:
				flag = false;
				return String.valueOf(bookingDetails.get(tempBookingId));
			default:
				return result+" Need Proper Change";
			}
		}
		return "Payment Failed";
	}
	
	public Seating viewPassenger(int passengerId) throws ManualException
	{
		Seating seat = bookingDetails.get(passengerId);
		if(seat ==null)
		{
			throw new ManualException("Passenger Not Found");
		}
		return seat;
	}
	

	 public String mealOrders(String flightNumber) throws ManualException
		{
			Map<String,Seat> map = flightSeater.get(flightNumber);
			
			int count = 0;
			
			if(map==null)
			{
				throw new ManualException("Invalid Flight Number");
			}
			
			List<String> list = new ArrayList<>();
			
			for(String seatNo : map.keySet())
			{
				Seat seat = map.get(seatNo);

				if(seat.isMeal())
				{
					count++;
					list.add(seat.getSeatNo());
				}
			}
			if(count == 0)
			{
				return "No Meals Ordered";
			}
			
			return "Meals Order  Seats : "+count+"\n"+list;
	}
	 
	 public String ticketCancellation(int bookingId) throws ManualException
	 {
		 Seating seating = bookingDetails.get(bookingId);
		 
		 if(seating==null)
		 {
			 throw new ManualException("Invalid Booked Id");
		 }
		 
		 seating.setMeal(false);
		 
		 seating.setCancellationStatus(true);
		 
		 Seat seat = flightSeater.get(seating.getFlightNumber()).get(seating.getSeatNo());
		 
		 seat.setSeatStatus(false);
		 
		 seat.setMeal(false);
		 
		 int price = seating.getPrice()-200;
		 
		 return "Excluding Cancellation Fee \n Your Balance : "+price;
		 
	 }
	 
	 
	
}
