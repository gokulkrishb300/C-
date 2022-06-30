package api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.Points;
import accountdeclare.TravelHistory;
import json.Json;
import manualexception.ManualException;

public class CallTaxiAPI {
	
	private int customerId = 0;
	
	private int bookingId = 1;
	
	private int noOfTaxis = 0;
	
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	JSONObject bookingDetails = new JSONObject();
	JSONObject travelProgress  = new JSONObject();
	
	@SuppressWarnings("unchecked")
	private int customerId() throws ManualException													//CustomerId Generator	
	{
		keyMap.put("customerId", ++customerId+"");
		json.jsonWrite(keyMap, "KeyDetails.json");
		return customerId;
	}
	
	@SuppressWarnings("unchecked")
	private int bookingId(String taxiNo,short charges,String destination) throws ManualException 		//BookingId Generator
	{
		JSONObject jsonObj;
		try {
			jsonObj = readTaxi(taxiNo);
		} catch (ManualException e) 
		{
			throw new ManualException("Getting bookingId from Taxi-"+taxiNo+" becomes failed");
		}
		String bookingID = (String) jsonObj.get("No.of_Bookings");
		
		String earnings = (String) jsonObj.get("Earnings");
		
		int newEarnings = Integer.valueOf(earnings)+charges;
		
		int bookId = Integer.valueOf(bookingID);
		
		jsonObj.put("No.of_Bookings", String.valueOf(++bookId));
		
		jsonObj.put("Earnings", String.valueOf(newEarnings));
		
		jsonObj.put("Current-Location", destination);
		
		json.jsonWrite(jsonObj, "Taxi-"+taxiNo+".json");
		
		return bookingId++;
	}
	
	@SuppressWarnings("unchecked")
	public String putCustomer(Customer customer) throws ManualException 				//PutCustomer
	{
		try {
			customer.setCustomerId(customerId());
		} catch (ManualException e)
		{
			throw new ManualException("CustomerId Setting Failed");
		}
		String data = gson.toJson(customer);
		customerMap.put(customerId+"", data);
		json.jsonWrite(customerMap, "CustomerDetails.json");
		return "CustomerId for "+customer.getName()+" : "+customerId;
	}
	
	public Customer getCustomer(String customerId) throws ManualException 				//GetCustomer		
	{
		String data = (String) customerMap.get(customerId);
		
		if(data==null)
		{
			throw new ManualException("Customer Not Found");
		}
		
		Customer customer = gson.fromJson(data, Customer.class);
		
		return customer;
	}
	
	private JSONObject readTaxi(String taxiNo) throws ManualException					//TaxiRead
	{
		return json.jsonRead("Taxi-"+taxiNo+".json");
	}
	
	
	private JSONObject readCustomerDetails() throws ManualException						//CustomerRead
	{
		return json.jsonRead("CustomerDetails.json");
	}
	
	private JSONObject readKey() throws ManualException 								//KeyRead
	{
		return json.jsonRead("KeyDetails.json");
	}
	
	private JSONObject readBookings() throws ManualException							//BookingsRead
	{
		return json.jsonRead("Bookings.json");
	}
	
	private JSONObject readTravelHistory() throws ManualException
	{
		return json.jsonRead("Taxi_History.json");
	}
																						
	public String load() throws ManualException 												//load
	{
		try {
			keyMap = readKey();
		} catch (Exception e) 
		{
			throw new ManualException("Json KeyMap Loading Failed");
		}
		
		if(keyMap!=null)
		{
			String no_Of_Taxis = (String) keyMap.get("No.Of_Taxis");
			
			if(no_Of_Taxis==null) {
				throw new ManualException("");
			}
			
		
			noOfTaxis = Integer.valueOf(no_Of_Taxis);
			
			String customerID = (String) keyMap.get("customerId");
			
			customerId = Integer.valueOf(customerID);
			
			try {
				customerMap = readCustomerDetails();
			} catch (Exception e) 
			{
				throw new ManualException("Json CustomerMap Loading Failed");
			}
			
			if(customerMap!=null)
			{
				try
				{
				bookingDetails = readBookings();
				}
				catch(Exception e)
				{
					throw new ManualException("Json Bookings load Failed");
				}
				if(bookingDetails!=null)
				{
					travelProgress = readTravelHistory();
					
					if(travelProgress!=null)
					{
						return "Key Details Loaded\nCustomer Details Loaded\nBooking Details Loaded\nTravel History Loaded";
					}
					
				}
				
			}
		}
		return "Load Failed";
	}
	
	@SuppressWarnings("unchecked")
	public String nFleet(int noOfTaxi_s) throws ManualException 							//Fleet Initializer
	{
		noOfTaxis = noOfTaxi_s;
		keyMap.put("No.Of_Taxis", noOfTaxi_s+"");
		try {
			json.jsonWrite(keyMap, "KeyDetails.json");
		} catch (Exception e) 
		{
			throw new ManualException("Json Writing Failed for KeyDetails.json");
		}
		Formatter fmt = new Formatter();
		fmt.format("%s %16s %10s %10s\n","Taxi_Name", "No.of_Bookings","Earnings","Current-Location");
		for(int i = 1 ; i <= noOfTaxi_s ; i++)
		{
			JSONObject jsonTaxi = new JSONObject();
			jsonTaxi.put("Taxi-Name", i+"");
			jsonTaxi.put("No.of_Bookings", "0");
			jsonTaxi.put("Earnings", "0");
			jsonTaxi.put("Current-Location", String.valueOf(Points.A));
			json.jsonWrite(jsonTaxi, "Taxi-"+i+".json");
			fmt.format("%3s %11s %12s %15s\n","Taxi-"+jsonTaxi.get("Taxi-Name"),jsonTaxi.get("No.of_Bookings"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
		}
		return fmt+"Call Taxi Operator has a fleet of "+noOfTaxi_s+" cars";
	}
	
	public String readFleet() throws ManualException 										//Fleet Reader
	{
		Formatter fmt = new Formatter();
		
		try {
			keyMap = readKey();
		} catch (ManualException e)
		{
			throw new ManualException("KeyMap Failed to Read Fleet's");
		}
		fmt.format("%s %16s %10s %10s\n","Taxi_Name", "No.of_Bookings","Earnings","Current-Location");
		
		String value = (String) keyMap.get("No.Of_Taxis");
		
		int noOfTaxi = Integer.valueOf(value);
		
		for(int i = 1 ; i <= noOfTaxi ; i++)
		{
			JSONObject jsonTaxi = null;
			try {
				jsonTaxi = readTaxi(String.valueOf(i));
			} catch (ManualException e) 
			{
				throw new ManualException("Unavailabe Taxi File");
			}
			fmt.format("%3s %11s %12s %15s\n","Taxi-"+jsonTaxi.get("Taxi-Name"), jsonTaxi.get("No.of_Bookings"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
		}
		return "NoOfTaxis : "+noOfTaxi+"\n"+fmt;		
	}
	

	
	public int calculate(Booking booking)										//Charges Calculate between two points
	{
		String start = booking.getStartingPoint().toString();
		char start_c = start.charAt(0);
		
	    String destination = booking.getDestinationPoint().toString();
	    char destination_c = destination.charAt(0);
	    
		int value = Math.abs(start_c - destination_c)*15;
		return value;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String ticketBooking(Booking booking,TravelHistory travelHistory) throws ManualException				//Ticket Booking
	{
		Customer customer = getCustomer(String.valueOf(booking.getCustomerId()));
		
		if(customer==null)
		{
			throw new ManualException("Customer Not Found\nBooking Cancelled");
		}
		
		if(booking.getStartingPoint().equals(booking.getDestinationPoint()))
		{
			throw new ManualException("Starting and Destination shouldn't be same\nBooking Cancelled");
		}
		
		String taxiNo = null;
		String destination = null;
		String taxiName = null;
		try {
			taxiNo = searchTaxis(booking.getStartingPoint()+"");
			
			destination = booking.getDestinationPoint()+"";
		
			taxiName = "Taxi-"+taxiNo;
			
			booking.setTaxi(taxiName);
			
		} catch (Exception e)
		{
			throw new ManualException("Ticket Booking Failed at Taxi allocating\nBooking Cancelled");
		}
		short earnings = booking.getCharges();
		
		int bookingId = bookingId(taxiNo,earnings,destination);
		
		booking.setBookingId(bookingId);
		
		String data = gson.toJson(booking);
		
		String bookingID = "T"+taxiNo+"_"+bookingId;
		
		travelHistory.setBookingId(bookingID);
		
		bookingDetails.put(bookingId+"", data);
		
		String travelData = gson.toJson(travelHistory);
		
		JSONObject temp = (JSONObject) travelProgress.get(taxiName);
		
		if(temp==null)
		{
			temp = new JSONObject();
			
			travelProgress.put(taxiName, temp);
		}
		
		temp.put(bookingID, travelData);

		json.jsonWrite(bookingDetails, "Bookings.json");
		
		json.jsonWrite(travelProgress, "Taxi_History.json");
		
		return "Booking ID : "+booking.getBookingId()+"\n"+"Alloted Taxi : "+booking.getTaxi();
		
	}
	
	public Booking getBookedDetails(String bookedId) throws ManualException
	{
		String data = (String) bookingDetails.get(bookedId);
		
		if(data!=null)
		{
			Booking booked = gson.fromJson(data, Booking.class);
			return booked;
		}
		throw new ManualException("BookingID not found");
	}
	
	public Formatter getTravelHistory(String taxiName) throws ManualException							//Get TravelHistory
	{
		
		JSONObject jsonObj = (JSONObject) travelProgress.get(taxiName);
		
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %15s %10s\n","Booking Id","StartPoint","EndPoint","Start Time","End Time" ,"Booking Type", "Charges");
		if(jsonObj!=null)
		{
			@SuppressWarnings("unchecked")
			Set<String> set = jsonObj.keySet();
			
			for(String value : set)
			{
				String data = (String) jsonObj.get(value);
				
				TravelHistory travelHistory  = gson.fromJson(data, TravelHistory.class);
				
				fmt.format("%s %10s %10s %15s %10s %10s %15s\n", travelHistory.getBookingId(),
						travelHistory.getStartingPoint(),travelHistory.getEndPoint(),
						travelHistory.getStartTime(),travelHistory.getEndTime(),travelHistory.getBookingType(),travelHistory.getCharges());
			}
			return fmt;
			
		}
		
		throw new ManualException("Taxi-Name Unavailable");
	}
	
	public String searchTaxis(String point) throws ManualException 								//Search Taxis Normal
	{
        List<String> list = new ArrayList<>();
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject json;
			try {
				json = readTaxi(i+"");
			} catch (Exception e) {
				//
				throw new ManualException("Read Failed");
			}
			String location = json.get("Current-Location")+"";
			
			if(location.equals(point))
			{
				list.add( json.get("Taxi-Name")+"");
			}
		}
		int size = list.size();
		if(size==0)
		{
			return "Oops!No Taxi's Available";
		}
		if(size == 1)
		{
			return list.get(0);
		}
		return searchTaxisByEarnings(point);
	}
	
	public String searchTaxisByEarnings(String point) throws ManualException					//Search Taxis by Earnings
	{
		Set<String> set = new TreeSet<>();
		
		int minEarnings = Integer.MAX_VALUE;
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject json = readTaxi(i+"");
			String location = json.get("Current-Location")+"";
			String earnings = json.get("Earnings")+"";
			int earn = Integer.valueOf(earnings);
			
			if(minEarnings > earn && location.equals(point))
			{
				minEarnings = earn;
				set.add(json.get("Taxi-Name")+"");
			}
		}
		int size = set.size();
		String taxi[] = set.toArray(new String[size]);
//		if(size==0)
//		{
//			return "Oops!No Taxi's Available";
//		}
		//System.out.println("Search Taxi By Earnings : "+taxi[taxi.length-1]);
		return taxi[taxi.length-1];
	}
}
