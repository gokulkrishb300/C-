package zkartapi;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Set;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Customer;
import accountdeclare.Inventory;
import json.Json;
import manualexception.ManualException;

public class ZKartAPI {
//	private int s_no = 0;
	
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();
	JSONObject inventoryMap = new JSONObject();
	JSONObject cartMap = new JSONObject();
	
//	@SuppressWarnings("unchecked")
//	private int getSerialNumber() throws ManualException
//	{
//		keyMap.put("Serial_Number", (++s_no)+"");
//		json.jsonWrite(keyMap, "KeyDetails.txt");
//		return s_no;
//	}
	
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
	
	public Formatter getCustomerFormat(String userName) throws ManualException
	{
		Customer customer = getCustomer(userName);
		
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s\n","UserName/Email","EncryptedPwd","Name","Mobile","Credit");
		
		fmt.format("%s %10s %15s %11s %5s\n", customer.getUserName(),customer.getPassword(),customer.getName(),
				customer.getMobile(),customer.getCredit());
		
		return fmt;
	}
	
	private JSONObject readCustomerDetails() throws ManualException						//CustomerRead
	{
		return json.jsonRead("zusers_db.txt");
	}
	
	private JSONObject readInventory() throws ManualException
	{
		return json.jsonRead("z-kart_db.txt");
	}
	
	private JSONObject readCart() throws ManualException 
	{
		return json.jsonRead("CartDetails.txt");
	}
	
	public String load() throws ManualException
	{
		customerMap = readCustomerDetails();
		
		if(customerMap!=null)
		{		
			inventoryMap = readInventory();
				
			if(inventoryMap!=null)
			{
				cartMap = readCart();
				
				if(cartMap!=null)
				{
					return "CustomerMap Loaded\nInventory Loaded\nCart Details Loaded";
				}
			}
		}

		return "Load Failed";
	}
	
	
	public String login(String userName) throws ManualException
	{	
		if(customerMap.get(userName)==null)
		{
			return "";
		}
		
		Customer customer = getCustomer(userName);
		
		return "Welcome to Z-Kart, "+customer.getName()+"!";
	}
	
	public Formatter login(String userName , String pwd) throws ManualException
	{
			Customer customer = getCustomer(userName);
			
			if(customer!=null)
			{
				if((pwd.hashCode())==Integer.valueOf(customer.getPassword()))
				{
					return inventoryFormatWithDiscount();
				}
			}
	      throw new ManualException("Incorrect Password");
	}
	
	@SuppressWarnings("unchecked")
	public String initializeInventory(Inventory inventory) throws ManualException
	{
		String category = inventory.getCategory();
		
		JSONObject jsonObj = (JSONObject) inventoryMap.get(category);
		
		if(jsonObj==null)
		{
			jsonObj = new JSONObject();
			
			inventoryMap.put(category, jsonObj);
		}
		
		String brand = inventory.getBrand();
		
		String data = gson.toJson(inventory);
		
		jsonObj.put(brand, data);

		json.jsonWrite(inventoryMap, "z-kart_db.txt");
		
		return inventoryFormat()+"Inventory Intialized";
	}
	
	@SuppressWarnings("unchecked")
	public String addStocks(Inventory inventory) throws ManualException
	{
		String category = inventory.getCategory();
		
		JSONObject jsonObj = (JSONObject) inventoryMap.get(category);
		
		if(jsonObj==null)
		{
			jsonObj = new JSONObject();
			
			inventoryMap.put(category, jsonObj);
		}
		
		String brand = inventory.getBrand();

		String inventoryData = (String) jsonObj.get(brand);
		
		Inventory inventoryPojo = gson.fromJson(inventoryData, Inventory.class);
		
		int stocks = inventoryPojo.getStock();
		
		inventoryPojo.setStock((short)(stocks+inventory.getStock()));
		
		inventoryPojo.setDiscount(inventory.getDiscount());
		
		String updatedData = gson.toJson(inventoryPojo);
		
		jsonObj.put(brand, updatedData);
		
		json.jsonWrite(inventoryMap, "z-kart_db.txt");
		
		return inventoryFormat()+"Inventory Stocks Added";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Formatter inventoryFormat()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONObject laptop = (JSONObject) inventoryMap.get("LAPTOP");
		
		if(laptop!=null)
		{
			Set laptopStocks = laptop.keySet();
			
			int laptopSize = laptopStocks.size();
			
			String laptopArr[] = new String[laptopSize];
			
			laptopStocks.toArray(laptopArr);
			
			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(laptopArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		JSONObject mobile = (JSONObject) inventoryMap.get("MOBILE");
		
		if(mobile!=null)
		{
			Set mobileStocks = mobile.keySet();
			
			int mobileSize = mobileStocks.size();
			
			String mobileArr[] = new String[mobileSize];
			
			mobileStocks.toArray(mobileArr);
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(mobileArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		JSONObject tablet = (JSONObject) inventoryMap.get("TABLET");
		
		if(tablet!=null)
		{
			Set tabletStocks = tablet.keySet();
			
			int tabletSize = tabletStocks.size();
			
			String tabletArr[] = new String[tabletSize];
			
			tabletStocks.toArray(tabletArr);
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(tabletArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());

			}
		}
		
		return fmt;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Formatter inventoryFormatWithDiscount()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("\n%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONObject laptop = (JSONObject) inventoryMap.get("LAPTOP");
		
		if(laptop!=null)
		{
			Set laptopStocks = laptop.keySet();
			
			int laptopSize = laptopStocks.size();
			
			String laptopArr[] = new String[laptopSize];
			
			laptopStocks.toArray(laptopArr);
			
			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(laptopArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				float discount = inventory.getDiscount();
				
				if(discount!=0)
				{
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),discount);
				}
			}
		}
		
		JSONObject mobile = (JSONObject) inventoryMap.get("MOBILE");
		
		if(mobile!=null)
		{
			Set mobileStocks = mobile.keySet();
			
			int mobileSize = mobileStocks.size();
			
			String mobileArr[] = new String[mobileSize];
			
			mobileStocks.toArray(mobileArr);
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(mobileArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				float discount = inventory.getDiscount();
				
				if(discount!=0)
				{
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),discount);
				}
			}
		}
		
		JSONObject tablet = (JSONObject) inventoryMap.get("TABLET");
		
		if(tablet!=null)
		{
			Set tabletStocks = tablet.keySet();
			
			int tabletSize = tabletStocks.size();
			
			String tabletArr[] = new String[tabletSize];
			
			tabletStocks.toArray(tabletArr);
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(tabletArr[i]);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				float discount = inventory.getDiscount();
				
				if(discount!=0)
				{
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),discount);
				}
			}
		}
		
		return fmt;
	}
	
	@SuppressWarnings("unchecked")
	public Formatter customerFormat()
	{
		Formatter fmt = new Formatter();
		
		@SuppressWarnings("rawtypes")
		Set userName = customerMap.keySet();
		
		int userNameSize = userName.size();
		
		String userNamesArr[] = new String[userNameSize];
		
		userName.toArray(userNamesArr);
		
		fmt.format("%s %10s %10s %10s %10s\n", "UserName/Email","EncryptedPwd","Name","Mobile","Credit");
		
		for(int i = 0 ; i < userNameSize ; i++)
		{
			String data = (String) customerMap.get(userNamesArr[i]);
			
			Customer customer = gson.fromJson(data, Customer.class);
			
			fmt.format("%s %10s %15s %11s %5s\n", customer.getUserName(),customer.getPassword(),customer.getName(),
					customer.getMobile(),customer.getCredit());
			
		}
		
		return fmt;
	}
	
	@SuppressWarnings("unchecked")
	public String addingCart(String userName, String item) throws ManualException
	{
		JSONArray getArr =  (JSONArray) cartMap.get(userName);
		
		if(getArr==null)
		{
			JSONArray jsonArr = new JSONArray();
			
			jsonArr.add(item);
			
			cartMap.put(userName, jsonArr);
			
			try {
				json.jsonWrite(cartMap, "CartDetails.txt");
							
			} catch (Exception e) 
			{
				throw new ManualException("Cart Adding failed");
			}
		}
		else
		{	
			getArr.add(item);
			
			cartMap.put(userName, getArr);
			
			try
			{
				json.jsonWrite(cartMap, "CartDetails.txt");
			}
			catch(Exception e)
			{
				throw new ManualException("Cart Adding Failed");
			}
		}
		return "Item Added in Cart Successfully";

	}
	
	public void viewCart(String userName)
	{
		
	}
	
}
