package zkartapi;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Customer;
import json.Json;
import manualexception.ManualException;

public class ZKartAPI {
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();

	

	
	@SuppressWarnings("unchecked")
	public String putCustomer(Customer customer) throws ManualException 								//PutCustomer
	{
		String userName = customer.getUserName();
		String data = gson.toJson(customer);
		customerMap.put(userName, data);
		json.jsonWrite(customerMap, "zusers_db.txt");
		return "Welcome to Z-Kart, "+customer.getName();
	}
	
	public Customer getCustomer(String userName) throws ManualException 				//GetCustomer		
	{
		String data = (String) customerMap.get(userName);
		
		if(data==null)
		{
			throw new ManualException("Customer Not Found");
		}
		
		Customer customer = gson.fromJson(data, Customer.class);
		
		return customer;
	}
	
	private JSONObject readCustomerDetails() throws ManualException						//CustomerRead
	{
		return json.jsonRead("zusers_db.txt");
	}
	
	
	public String load() throws ManualException
	{
		customerMap = readCustomerDetails();
		
		if(customerMap!=null)
		{
			return "CustomerMap Loaded";
		}
		return "Load Failed";
	}
	
	
	public String login(String userName) throws ManualException
	{
			Customer customer = getCustomer(userName);
		
			if(customer==null)
			{
				throw new ManualException("UserName thappa potrukada venna");
			}
			
			return "Welcome to Z-Kart, "+customer.getName()+"!";
	}
	
	public Customer login(String userName , String pwd) throws ManualException
	{
	
			Customer customer = getCustomer(userName);
			
			if(customer!=null)
			{
				if((pwd.hashCode())==Integer.valueOf(customer.getPassword()))
				{
					return customer;
				}
			}
	      throw new ManualException("Incorrect Password");
	}
	
	
	
}
