package api;


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
import accountdeclare.Points;

import accountdeclare.TravelHistory;
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
	JSONObject bookingDetails = new JSONObject();
	JSONObject travelProgress  = new JSONObject();
	JSONObject timingMap = new JSONObject();
	
	@SuppressWarnings("unchecked")
	private int customerId() throws ManualException													//CustomerId Generator	
	{
		keyMap.put("customerId", ++customerId+"");
		json.jsonWrite(keyMap, "KeyDetails.json");
		return customerId;
	}
	
	String taxiHistoryId = null;
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
		String noOfPersons = (String) jsonObj.get("No.of_Persons");
		
		String earnings = (String) jsonObj.get("Earnings");
		
		int newEarnings = Integer.valueOf(earnings)+charges;
		
		int person = Integer.valueOf(noOfPersons);
		
		if(person == 3 )
		{
			
		}
		
		jsonObj.put("No.of_Persons", String.valueOf(++person));
		
		taxiHistoryId = person+"";
		
		jsonObj.put("Earnings", String.valueOf(newEarnings));
		
		jsonObj.put("Current-Location", destination);
		
		json.jsonWrite(jsonObj, "Taxi-"+taxiNo+".json");
		
		keyMap.put("bookingId", String.valueOf(++bookingId));
		
		json.jsonWrite(keyMap, "KeyDetails.json");
		
		return bookingId;
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
	
	private JSONObject readTimingMap() throws ManualException 
	{
		return json.jsonRead("Taxi-Timings.json");
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
			String no_Of_Taxis = (String) keyMap.get("No.of_Taxis");
			
			String bookingID = (String) keyMap.get("bookingId");
			
			
			if(no_Of_Taxis==null || bookingID == null) {
				throw new ManualException("key values unavailable");
			}
			
			bookingId = Integer.valueOf(bookingID);
			
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
						timingMap = readTimingMap();
						
						if(timingMap!=null)
						{
							return "Key Details Loaded\nCustomer Details Loaded\nBooking Details Loaded\nTravel History Loaded\nTiming Details Loaded";
						}
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
		keyMap.put("No.of_Taxis", noOfTaxi_s+"");
		try {
			json.jsonWrite(keyMap, "KeyDetails.json");
		} catch (Exception e) 
		{
			throw new ManualException("Json Writing Failed for KeyDetails.json");
		}
		Formatter fmt = new Formatter();
		fmt.format("%s %16s %10s %10s\n","Taxi_Name", "No.of_Persons","Earnings","Current-Location");
		for(int i = 1 ; i <= noOfTaxi_s ; i++)
		{
			JSONObject jsonTaxi = new JSONObject();
			jsonTaxi.put("Taxi-Name", i+"");
			jsonTaxi.put("No.of_Persons", "0");
			jsonTaxi.put("Earnings", "0");
			jsonTaxi.put("Current-Location", String.valueOf(Points.A));
			json.jsonWrite(jsonTaxi, "Taxi-"+i+".json");
			fmt.format("%3s %11s %12s %15s\n","Taxi-"+jsonTaxi.get("Taxi-Name"),jsonTaxi.get("No.of_Persons"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
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
		fmt.format("%s %16s %10s %10s\n","Taxi_Name", "No.of_Persons","Earnings","Current-Location");
		
		String value = (String) keyMap.get("No.of_Taxis");
		
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
			fmt.format("%3s %11s %12s %15s\n","Taxi-"+jsonTaxi.get("Taxi-Name"), jsonTaxi.get("No.of_Persons"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
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
	
	private void charges(Booking booking,TravelHistory travelHistory,byte bookingType)
	{
		if(bookingType == 1)
		{
			short temp = (short)(calculate(booking)*10);
			booking.setCharges(temp);
			travelHistory.setCharges(temp);
		}
		else
		{
			short temp = (short)(calculate(booking)*10*0.4);
			booking.setCharges(temp);
			travelHistory.setCharges(temp);
		}
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
	private String endTime(String taxiNo,String startTime , String startPoint, String endPoint,String bookingType) throws ManualException			//predicting End Time
	{
		List<String> list = new ArrayList<>();
		
		char stPoint = startPoint.charAt(0);
	
		char ePoint = endPoint.charAt(0);
		
		int value = Math.abs(ePoint-stPoint);
		
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
		
		
		for(int i = 1 ; i <= value ; i++)
		{
			minutes += 15;
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
		
		if(bookingType.equals("2"))
		{
			for(String time : list)
			{
			timingMap.put(time,taxiNo);
			}
			json.jsonWrite(timingMap, "Taxi-Timings.json");
		}
//		System.out.println(hour+"."+minutes+""+meridiem);
		return hour+"."+minutes+""+meridiem;
	}
	
	@SuppressWarnings("unchecked")
	public String dispatchPassenger(String taxiNo) throws ManualException
	{
		JSONObject jsonObj = readTaxi(taxiNo);
		
		jsonObj.put("No.of_Persons", "0");
		
		json.jsonWrite(jsonObj, "Taxi-"+taxiNo+".json");
		
		return "Taxi "+taxiNo+" freed";
	}
	
	
	private void alert() throws ManualException
	{
		int count = 0;
		for(int i = 1 ; i <=noOfTaxis ; i++)
		{
			JSONObject jsonObj = readTaxi(i+"");
	
			if(jsonObj.get("No.of_Persons").equals("3"))
			{
				count++;
			}
			if(count == noOfTaxis)
			{
				throw new ManualException("All Taxis are full\nBooking Cancelled");
			}	
		}
	}

	@SuppressWarnings("unchecked")
	public String ticketBooking(Booking booking,TravelHistory travelHistory) throws ManualException				//Ticket Booking
	{

		alert();
		
		Customer customer = getCustomer(String.valueOf(booking.getCustomerId()));
		
		if(customer==null)
		{
			throw new ManualException("Customer Not Found\nBooking Cancelled");
		}
		
		if(booking.getStartingPoint().equals(booking.getDestinationPoint()))
		{
			throw new ManualException("Starting and Destination shouldn't be same\nBooking Cancelled");
		}
		
		String getTime = booking.getTime();

		String timingMapResult = (String) timingMap.get(getTime);
		
		if(timingMapResult!=null)
		{
		JSONObject jsonObj = readTaxi(timingMapResult);
		
		String noOfPerson = (String) jsonObj.get("No.of_Persons");
		
		String taxiDestination = (String) jsonObj.get("Current-Location");
		
		if(Integer.valueOf(noOfPerson)==3||!taxiDestination.equals(booking.getDestinationPoint()+""))
		{
			timingMapResult=null;
		}
		
		}
		
		String taxiNo = null;
		String destination = null;
		String taxiName = null;
		try {
			if(timingMapResult!=null)
			{
				taxiNo = timingMapResult;
				charges(booking,travelHistory,(byte) 2);
			}
			else
			{
			taxiNo = allotedTaxi(booking.getStartingPoint()+"");
			dispatchPassenger(taxiNo);
			}
			destination = booking.getDestinationPoint()+"";
		
			taxiName = "Taxi-"+taxiNo;
			
			booking.setTaxi(taxiName);
			
			charges(booking,travelHistory,booking.getBookingType()); 
		} catch (Exception e)
		{
			throw new ManualException("Ticket Booking Failed at Taxi allocating\nBooking Cancelled");
		}
		
		
		short earnings = booking.getCharges();
		
		int bookingId = bookingId(taxiNo,earnings,destination);
		
		booking.setBookingId(bookingId);
		
		String endTime = endTime(taxiNo, booking.getTime()+"",booking.getStartingPoint()+"",booking.getDestinationPoint()+"",booking.getBookingType()+"");
		
		String data = gson.toJson(booking);
		
		String bookingID = "T"+taxiNo+"_"+taxiHistoryId;
		
		travelHistory.setBookingId(bookingID);
		
		bookingDetails.put(bookingId+"", data);
		
		travelHistory.setEndTime(endTime);
		
		String travelData = gson.toJson(travelHistory);
		
		JSONObject temp = (JSONObject) travelProgress.get(taxiName);
		
		if(temp==null)
		{
			temp = new JSONObject();
			
			travelProgress.put(taxiName, temp);
		}
		
		temp.put(taxiHistoryId, travelData);

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
	
	public Formatter getTravelHistory(String taxiNo) throws ManualException							//Get TravelHistory
	{
		
		JSONObject jsonObj = (JSONObject) travelProgress.get("Taxi-"+taxiNo);
		
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
	
	public String allotedTaxi(String point) throws ManualException
	{
		List<String> list = new ArrayList<>();
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject jsonObj = readTaxi(i+"");
			
			String currentLocation = (String) jsonObj.get("Current-Location");
			
			if(point.equals(currentLocation))
			{
				list.add(i+"");
			}
		}
		
		int currentLocationTaxis = list.size();
		
		if(currentLocationTaxis == 1)
		{
			return list.get(0);
		}
		
		return taxiWithLowEarnings(point);
	}
	
	public String taxiWithLowEarnings(String point) throws ManualException
	{
		int minEarnings = Integer.MAX_VALUE;
		
		int oldMinDistance = Integer.MAX_VALUE;
		
		List<String> list = new ArrayList<>();
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject jsonObj = readTaxi(i+"");
			
			String taxiLocation = (String) jsonObj.get("Current-Location");
			
			String taxiEarnings = (String) jsonObj.get("Earnings");
			
			int newMinDistance = Math.abs(taxiLocation.charAt(0)-point.charAt(0));
			
			int actualEarnings = Integer.valueOf(taxiEarnings);
			
			if(taxiLocation.equals(point) || (newMinDistance <= oldMinDistance))
			{
				oldMinDistance = newMinDistance;
				
					if(actualEarnings < minEarnings)
					{
						minEarnings = actualEarnings;
					
					    list.add(i+"");
					}
			}
		}
		int size = list.size();
		
		if(size == 1)
		{
			return list.get(0);
		}
		
		return list.get(size-1);
	}
	
	
}
