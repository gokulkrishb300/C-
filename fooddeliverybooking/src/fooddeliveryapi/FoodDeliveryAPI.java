package fooddeliveryapi;


import java.util.Formatter;

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
		return json.jsonRead("BookingDetails.json");
	}
	
	public JSONObject readDE(String deNo) throws ManualException					//DeliveryExecutvieRead
	{
		return json.jsonRead("DE"+deNo+".json");
	}
	
	private JSONObject readDelivery() throws ManualException
	{
		return json.jsonRead("DeliveryHistory.json");
	}
	
	private JSONObject readTiming() throws ManualException
	{
		return json.jsonRead("TimingDetails.json");
	}
	
	public String load() throws ManualException 												//load
	{
		
			keyMap = readKey();
	
		   if(keyMap!=null)
		   {
			String noOfDE = (String) keyMap.get("No.of_DE");
			
			String bookingID = (String) keyMap.get("bookingId");
			
			String tripNo = (String) keyMap.get("Trip");
			
			String customerID = (String) keyMap.get("customerId");
			
			trip = Integer.valueOf(tripNo);
			
			noOfDeliveryExecutives = Integer.valueOf(noOfDE);
			
			bookingId = Integer.valueOf(bookingID);
			
			customerId = Integer.valueOf(customerID);
			
			customerMap = readCustomerDetails();

			if(customerMap!=null)
			{
				bookingDetails = readBookings();

				if(bookingDetails!=null)
				{
					deliveryHistory = readDelivery();
					
					timingMap = readTiming();
					
					if(timingMap!=null)
					{
						return "Key Loaded\nCustomer Details Loaded\nBooking Details Loaded\nDelivery History Loaded\nTiming Details Loaded";
					}
				}
			}
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
	
	@SuppressWarnings("unchecked")
	public String validTime(String startTime,Booking booking, ExecutiveActivity executive) throws ManualException												//validatingStartTime
	{
		String regex = "(1[012]|[1-9])."+"[0-5][0-9](\\s)"+"?(?i)(am|pm)";
		
		Pattern compilePattern = Pattern.compile(regex);
		
		Matcher matcher = compilePattern.matcher(startTime);
		
		if(!matcher.matches())
		{
			throw new ManualException("Invalid Time\nBooking Cancelled");
		}
		
		String deNo = (String) timingMap.get(startTime);
		
		if(deNo!=null)
		{
			JSONObject jsonObj = readDE(deNo);
			
			String current_Location = (String) jsonObj.get("Deliver_Location");
			
			String destinationPoint = executive.getDestinationPoint().toString();
			
			
			if(!current_Location.equals(destinationPoint))
			{
				return "";
			}
			
			String order = (String) jsonObj.get("No.of_Order");
			
			if(order.equals("5"))
			{
				return "";
			}
			
			int noOfOrder = Integer.valueOf(order)+1;
			
			jsonObj.put("No.of_Order", noOfOrder+"");
			
			int deliveryCharge = 50+ 5*(noOfOrder-1);
			
			jsonObj.put("Deliver_Charges", deliveryCharge+"");
			
			String value1 = (String) jsonObj.get("Allowance");
			
			int allowance = Integer.valueOf(value1);
			
			int total = allowance + deliveryCharge;
			
			executive.setOrders((byte)noOfOrder);
			
			executive.setDeliveryCharge((byte)deliveryCharge);
			
			jsonObj.put("Total", total+"");
			
			json.jsonWrite(jsonObj, "DE"+deNo+".json");
			
			for(int i = 1; i <= trip ; i++)
			{
				String data = (String) deliveryHistory.get(i+"");
				
				ExecutiveActivity activity = gson.fromJson(data, ExecutiveActivity.class);
				
				if((activity.getDestinationPoint()+"").equals(destinationPoint))
				{
					executive.setTrip(activity.getTrip());
					executive.setExeNo(activity.getExeNo());
					executive.setPickupTime(activity.getPickupTime());
					executive.setDeliveryTime(activity.getDeliveryTime());
					String executiveData = gson.toJson(executive);
					deliveryHistory.put(activity.getTrip()+"", executiveData);
				}
			}
			
			json.jsonWrite(jsonObj, "DeliveryHistory.java");
			
			booking.setBookingId(++bookingId);
			
			booking.setTime(startTime);
			
			keyMap.put("bookingId", bookingId+"");
			
			json.jsonWrite(keyMap, "KeyDetails.json");
			
			String bookingData = gson.toJson(booking);
			
			bookingDetails.put(bookingId+"", bookingData);
			
			json.jsonWrite(bookingDetails, "BookingDetails.json");
			
			return "Booking ID : "+bookingId+"\nAlloted Delivery Executive : "+"DE"+deNo;
		}
		return "";
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
			jsonObj.put("Deliver_Location", Points.A+"");
			
			json.jsonWrite(jsonObj, "DE"+i+".json");
			fmt.format("%3s %11s %12s %11s\n","DE"+jsonObj.get("Executive"),jsonObj.get("Allowance"),jsonObj.get("Deliver_Charges"),jsonObj.get("Total"));
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
	
	public String beforeBooking() throws ManualException
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
			fmt.format("%3s %11s\n","DE"+jsonObj.get("Executive"),jsonObj.get("Deliver_Charges"));
		}
		return "\nAvailable Executives :\n"+fmt;		
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
	private void deliveryTime(Booking booking, ExecutiveActivity executive) throws ManualException
	{
		String startTime = booking.getTime();
		
		Set<String> set = new TreeSet<>();

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
		
		int condition = minutes+45;
		
	
		for(int i = minutes+1 ; i <= condition ; i++ )
		{
			minutes++;
			
			if(minutes >=60)
			{
				hour++;
				minutes -= 60;
			}
				String temp = String.valueOf(minutes);
				if(temp.length()==1)
				{
					temp ="0"+temp;
					set.add(hour+"."+temp+""+meridiem);
				}
				else
				{
			set.add(hour+"."+minutes+""+meridiem);
				}
		}
		
		String executiveNo = executive.getExeNo();
		
		int size = set.size();
	
		String[] time = new String[size];
		
		set.toArray(time);
		
		int count = 0;
		
		for(int i = 0 ;i < size ; i++)
		{
			count++;
			
			if(count == 15)
			{
				break;
			}
			timingMap.put(time[i],executiveNo);
		}
		executive.setPickupTime(time[14]);
		
		executive.setDeliveryTime(time[size-1]);
		
		json.jsonWrite(timingMap, "TimingDetails.json");
	}
	
	@SuppressWarnings("unchecked")
	public String deliveryBooking(Booking booking,ExecutiveActivity executive ) throws ManualException
	{	
		booking.setBookingId(++bookingId);
		
		keyMap.put("bookingId", bookingId+"");
		
		String deliveryExecutive = allotDeliveryExecutive();
		
		JSONObject jsonObj = readDE(deliveryExecutive);
		
		String value = (String) jsonObj.get("Allowance");
		
		int allowance = Integer.valueOf(value)+10;
		
		jsonObj.put("Allowance", allowance+"");
		
		String value1 = (String) jsonObj.get("Deliver_Charges");
		
		int deliveryCharges = Integer.valueOf(value1)+50;
		
		jsonObj.put("Deliver_Charges", deliveryCharges+"");
		
		int total = allowance+deliveryCharges;
		
		jsonObj.put("Total", total+"");
		
		jsonObj.put("Deliver_Location", booking.getDestinationPoint()+"");
		
		String value3 = (String) jsonObj.get("No.of_Order");
		
		int noOfOrder = Integer.valueOf(value3)+1;
		
		jsonObj.put("No.of_Order", noOfOrder+"");
		
		json.jsonWrite(jsonObj, "DE"+deliveryExecutive+".json");
		
		executive.setExeNo(deliveryExecutive);
		
		deliveryTime(booking,executive);
		
		executive.setTrip(++trip);
		
		keyMap.put("Trip", trip+"");
		
		json.jsonWrite(keyMap, "KeyDetails.json");
		
		int order = executive.getOrders();
		
		executive.setOrders((byte)++order);
		
		executive.setDeliveryCharge((byte)50);
		
		String bookingData = gson.toJson(booking);
		
		bookingDetails.put(bookingId+"", bookingData);
		
		String executiveData = gson.toJson(executive);
		
		deliveryHistory.put(trip+"", executiveData);
		
		json.jsonWrite(bookingDetails, "BookingDetails.json");
		
		json.jsonWrite(deliveryHistory, "DeliveryHistory.json");
		
		return "Booking ID : "+bookingId+"\nAlloted Delivery Executive : "+"DE"+deliveryExecutive;
	}
	
	
	@SuppressWarnings("unchecked")
	public String reduceOrder(String deNo) throws ManualException
	{
		JSONObject jsonObj = readDE(deNo);
		
		jsonObj.put("No.of_Order","0");

		json.jsonWrite(jsonObj, "DE"+deNo+".json");
		
		return "Delivery Executive : "+deNo+" Order Freed";
	}
	
	public Formatter deliveryHistory() throws ManualException
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s %10s %10s\n","TRIP","EXECUTIVE","RESTAURANT",
				"DESTINATION POINT","ORDERS","PICK-UP_TIME","DELIVERY_TIME","DELIVERY CHARGE");
		
		String data = null;
		
		try
		{
			for(int i = 1 ; i <= trip ; i++)
			{
				data = (String) deliveryHistory.get(i+"");
				
				ExecutiveActivity activity = gson.fromJson(data, ExecutiveActivity.class);
				
				fmt.format("%s %10s %10s %10s %18s %10s %10s %10s\n", activity.getTrip(),"DE"+activity.getExeNo(),
						activity.getRestaurant(),activity.getDestinationPoint(),activity.getOrders(),
						activity.getPickupTime(),activity.getDeliveryTime(),activity.getDeliveryCharge());
			}
		}
		catch(Exception e)
		{
			throw new ManualException("Edho Prechana");
		}
		
		return fmt;
	}
}
