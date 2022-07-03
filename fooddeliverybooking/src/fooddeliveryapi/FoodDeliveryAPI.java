package fooddeliveryapi;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.ExecutiveActivity;
import accountdeclare.Points;
import json.Json;
import manualexception.ManualException;

public class FoodDeliveryAPI {
	
	private int customerId = 0;
	private int bookingId = 0;
	private int noOfDeliveryExecutives = 0;
	private int trip = 0;
	
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	JSONObject bookingDetails = new JSONObject();
	JSONObject deliveryHistory = new JSONObject();
	JSONObject timingMap = new JSONObject();
	
	@SuppressWarnings("unchecked")
	private int customerId() throws ManualException													//CustomerId Generator	
	{
		keyMap.put("customerId", ++customerId+"");
		json.jsonWrite(keyMap, "KeyDetails.json");
		return customerId;
	}
	
	@SuppressWarnings("unchecked")
	public String putCustomer(Customer customer) throws ManualException 								//PutCustomer
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
	
	private JSONObject readDE(String deNo) throws ManualException					//DeliveryExecutvieRead
	{
		return json.jsonRead("DE-"+deNo+".json");
	}
	
	public String load() throws ManualException 												//load
	{
		try {
			
			keyMap = readKey();
	
		   if(keyMap!=null)
		   {
			String noOfDE = (String) keyMap.get("No.of_DE");
			
			String bookingID = (String) keyMap.get("bookingId");
			
			noOfDeliveryExecutives = Integer.valueOf(noOfDE);
			
			bookingId = Integer.valueOf(bookingID);
			
			String customerID = (String) keyMap.get("customerId");
			
			customerId = Integer.valueOf(customerID);
			
			customerMap = readCustomerDetails();

			if(customerMap!=null)
			{
				bookingDetails = readBookings();

				if(bookingDetails!=null)
				{
					return "Key Loaded\nCustomer Details Loaded\nBooking Details Loaded";
				}
			}
		  }   
		}
		catch(Exception e)
		{
			throw new ManualException("Json Bookings load Failed");
		}
		return "Load Failed";
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
	
	public void validTime(String startTime) throws ManualException												//validatingStartTime
	{
		String regex = "(1[012]|[1-9])."+"[0-5][0-9](\\s)"+"?(?i)(am|pm)";
		
		Pattern compilePattern = Pattern.compile(regex);
		
		Matcher matcher = compilePattern.matcher(startTime);
		
		if(!matcher.matches())
		{
			throw new ManualException("Invalid Time\nBooking Cancelled");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public String nFleet(int noOfDE) throws ManualException 													//Fleet Initializer
	{
		noOfDeliveryExecutives = noOfDE;
		keyMap.put("No.of_DE", noOfDE+"");
		try {
			json.jsonWrite(keyMap, "KeyDetails.json");
		} catch (Exception e) 
		{
			throw new ManualException("Json Writing Failed for KeyDetails.json");
		}
		Formatter fmt = new Formatter();
		fmt.format("%s %10s %10s %6s\n","Executive", "Allowance","Deliver_Charges","Total");
		for(int i = 1 ; i <= noOfDeliveryExecutives ; i++)
		{
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Executive", i+"");
			jsonObj.put("Allowance", "0");
			jsonObj.put("No.of_Order", "0");
			jsonObj.put("Deliver_Charges", "0");
			jsonObj.put("Total", "0");
			
			json.jsonWrite(jsonObj, "DE"+i+".json");
			fmt.format("%3s %11s %12s %11s\n","DE-"+jsonObj.get("Executive"),jsonObj.get("Allowance"),jsonObj.get("Deliver_Charges"),jsonObj.get("Total"));
		}
		return fmt+"Food Delivery Company has a fleet of "+noOfDeliveryExecutives+" cars";
	}
	
	public String afterBooking() throws ManualException 															//Fleet Reader
	{
		Formatter fmt = new Formatter();
		
		try {
			keyMap = readKey();
		} catch (ManualException e)
		{
			throw new ManualException("KeyMap Failed to Read Fleet's");
		}
		fmt.format("%s %10s %10s %6s\n","Executive","Allowance","Deliver Charges","Total");
		
		String value = (String) keyMap.get("No.of_DE");
		
		int noOfDE = Integer.valueOf(value);
		
		for(int i = 1 ; i <= noOfDE ; i++)
		{
			JSONObject jsonObj = null;
			try {
				jsonObj = readDE(i+"");
			} catch (ManualException e) 
			{
				throw new ManualException("Unavailabe Executive File");
			}
			fmt.format("%3s %11s %12s %11s\n","DE"+jsonObj.get("Executive"), jsonObj.get("Allowance"),jsonObj.get("Deliver_Charges"),jsonObj.get("Total"));
		}
		return "NoOfDeliveryExecutives	 : "+noOfDE+"\n"+fmt;		
	}
	
	public Formatter beforeBooking() throws ManualException
	{
		Formatter fmt = new Formatter();
		
		try {
			keyMap = readKey();
		} catch (ManualException e)
		{
			throw new ManualException("KeyMap Failed to Read Fleet's");
		}
		fmt.format("%s %10s\n","Executive","Delivery Charge Earned");
		
		String value = (String) keyMap.get("No.of_DE");
		
		int noOfDE = Integer.valueOf(value);
		
		for(int i = 1 ; i <= noOfDE ; i++)
		{
			JSONObject jsonObj = null;
			try {
				jsonObj = readDE(i+"");
			} catch (ManualException e) 
			{
				throw new ManualException("Unavailabe Executive File");
			}
			fmt.format("%3s %11s %12s %11s\n","DE"+jsonObj.get("Executive"),jsonObj.get("Deliver_Charges"));
		}
		return fmt;		
	}
	
	public String allotDeliveryExecutive() throws ManualException
	{
		int minimum = Integer.MAX_VALUE;
		
		Set<String> set = new TreeSet<>();
		
		for(int i = 1 ; i <= noOfDeliveryExecutives ; i++)
		{
			JSONObject jsonObj = readDE(i+"");
			
			String value = (String) jsonObj.get("Deliver_Charges");
			
			int deMinimum = Integer.valueOf(value);
			
			if(deMinimum < minimum)
			{
				minimum = deMinimum;
				
				set.add(i+"");
			}
		}
		
		int size = set.size();
		
		String[] minimumArr  = new String[size];
		
		set.toArray(minimumArr);
		
		if(size == 0)
		{
			throw new ManualException("Alloting Delivery Executive Failure");
		}
		if(size == 1)
		{
			return minimumArr[0];
		}
		return minimumArr[size-1];
	}
	
	@SuppressWarnings("unchecked")
	private String deliveryTime(Booking booking, ExecutiveActivity executive)
	{
		String startTime = booking.getTime();
		
		List<String> list = new ArrayList<>();

		String[] hr = startTime.split("\\.");
		
		int hour = Integer.valueOf(hr[0]);
		
		String min = hr[1].substring(0,2);
		
		int minutes = Integer.valueOf(min);
		
		String meridiem = null;
		
		if(startTime.contains("AM"))
		{
			meridiem = "AM";
		}
		else
		{
			meridiem = "PM";
		}
		
		int condition = minutes+15;
		
		for(int i = minutes+1 ; i <= condition ; i++)
		{
		
			if(minutes >=60)
			{
				hour++;
				minutes -= 60;
				String temp = String.valueOf(minutes);
				if(temp.length()==1)
				{
					temp ="0"+temp;
					list.add(hour+"."+temp+""+meridiem);
				}
				else
				{
					list.add(hour+"."+minutes+""+meridiem);
				}
			}
			else
			{
			list.add(hour+"."+minutes+""+meridiem);
			}
		}
		
		String executiveNo = executive.getExeNo();
		
		for(String time : list)
		{
			timingMap.put(time, executiveNo);
		}
		
		int size = list.size();
		
		executive.setPickupTime(list.get(size-1));
		
		executive.setDeliveryTime(meridiem);
	}
	
	public void deliveryBooking(Booking booking,ExecutiveActivity executive ) throws ManualException
	{
		String customerId = booking.getCustomerId()+"";
		
		Customer customer = getCustomer(customerId);
		
		if(customer==null)
		{
			throw new ManualException("Customer Not Found\nDelivery Cancel");
		}
		
		
		booking.setBookingId(++bookingId);
		
		String deliveryExecutive = allotDeliveryExecutive();
		
		executive.setExeNo(deliveryExecutive);
		
		String deliveryTime = deliveryTime(booking,executive);
		
		
	}
	

}
