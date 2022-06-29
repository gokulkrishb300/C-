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
import json.Json;
import manualexception.ManualException;

public class CallTaxiAPI {
	
	private int customerId = 0;
	
	private int bookingId = 0;
	
	private int noOfTaxis = 0;
	
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	
	@SuppressWarnings("unchecked")
	private int customerId() throws ManualException
	{
		keyMap.put("customerId", String.valueOf(++customerId));
		json.jsonWrite(keyMap, "KeyDetails.json");
		return customerId;
	}
	
	@SuppressWarnings("unchecked")
	private int bookingId(String taxiNo) throws ManualException
	{
		JSONObject jsonObj = readTaxi(taxiNo);
		String bookingID = (String) jsonObj.get("bookingId");
		bookingId = Integer.valueOf(bookingID);
		jsonObj.put("bookingId", String.valueOf(++bookingId));
		json.jsonWrite(jsonObj, "Taxi-"+taxiNo+".json");
		return bookingId;
	}
	
	@SuppressWarnings("unchecked")
	public String putCustomer(Customer customer) throws ManualException
	{
		customer.setCustomerId(customerId());
		String data = gson.toJson(customer);
		customerMap.put(String.valueOf(customerId), data);
		json.jsonWrite(customerMap, "CustomerDetails.json");
		return "CustomerId for "+customer.getName()+" : "+customerId;
	}
	
	public Customer getCustomer(String customerId) throws ManualException 
	{
		String data = (String) customerMap.get(customerId);
		
		if(data==null)
		{
			throw new ManualException("Customer Not Found");
		}
		
		Customer customer = gson.fromJson(data, Customer.class);
		
		return customer;
	}
	
	private JSONObject readTaxi(String taxiNo) throws ManualException
	{
		return json.jsonRead("Taxi-"+taxiNo+".json");
	}
	
	
	private JSONObject readCustomerDetails() throws ManualException
	{
		return json.jsonRead("CustomerDetails.json");
	}
	
	private JSONObject readKey() throws ManualException 
	{
		return json.jsonRead("KeyDetails.json");
	}
	
	public String load() throws ManualException
	{
		keyMap = readKey();
		
		if(keyMap!=null)
		{
			String no_Of_Taxis = (String) keyMap.get("No.Of_Taxis");
		
			noOfTaxis = Integer.valueOf(no_Of_Taxis);
			
			String customerID = (String) keyMap.get("customerId");
			
			customerId = Integer.valueOf(customerID);
			
			customerMap = readCustomerDetails();
			
			if(customerMap!=null)
			{
				return "Key Details Loaded\nCustomer Details Loaded";
			}
		}
		return "Load Failed";
	}
	
	@SuppressWarnings("unchecked")
	public String nFleet(int noOfTaxi_s) throws ManualException
	{
		keyMap.put("No.Of_Taxis", String.valueOf(noOfTaxi_s));
		json.jsonWrite(keyMap, "KeyDetails.json");
		Formatter fmt = new Formatter();
		fmt.format("%s %12s %10s %10s\n","Taxi Name", "Booking-Id","Earnings","Current-Location");
		for(int i = 1 ; i <= noOfTaxi_s ; i++)
		{
			JSONObject jsonTaxi = new JSONObject();
			jsonTaxi.put("Taxi-Name", "T_"+i);
			jsonTaxi.put("bookingId", "1");
			jsonTaxi.put("Earnings", "0");
			jsonTaxi.put("Current-Location", String.valueOf(Points.A));
			json.jsonWrite(jsonTaxi, "Taxi-"+i+".json");
			fmt.format("%3s %15s %8s %15s\n",i, "T"+i+"_"+jsonTaxi.get("bookingId"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
		}
		return fmt+"Call Taxi Operator has a fleet of "+noOfTaxi_s+" cars";
	}
	
	private String currentTime(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		Date date = new Date(time);
		return sdf.format(date);
	}
	
	public int calculate(Booking booking)
	{
		String start = booking.getStartingPoint().toString();
		char start_c = start.charAt(0);
		
	    String destination = booking.getDestinationPoint().toString();
	    char destination_c = destination.charAt(0);
	    
		int value = Math.abs(start_c - destination_c)*15;
		return value;
	}
	public void ticketBooking(Booking booking)
	{
		booking.setTime(currentTime(System.currentTimeMillis()));
		

		
		
	}
	
	public String searchTaxis(String point) throws ManualException
	{
        List<String> list = new ArrayList<>();
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject json = readTaxi(String.valueOf(i));
			String location = (String) json.get("Current-Location");
			
			if(location.equals(point))
			{
				list.add((String) json.get("Taxi-Name"));
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
	
	public String searchTaxisByEarnings(String point) throws ManualException
	{
		Set<String> set = new TreeSet<>();
		
		int minEarnings = Integer.MAX_VALUE;
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject json = readTaxi(String.valueOf(i));
			String location = (String) json.get("Current-Location");
			String earnings = (String) json.get("Earnings");
			int earn = Integer.valueOf(earnings);
			
			if(minEarnings > earn && location.equals(point))
			{
				minEarnings = earn;
				set.add((String) json.get("Taxi-Name"));
			}
		}
		int size = set.size();
		String taxi[] = set.toArray(new String[size]);
//		if(size==0)
//		{
//			return "Oops!No Taxi's Available";
//		}
		return taxi[taxi.length-1];
	}
	
	
	
}
