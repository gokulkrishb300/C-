package zkartapi;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
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
		return "Welcome to Z-Kart!";
	}
	
	public String login(String userName , String password) throws ManualException
	{
			Customer customer = getCustomer(userName);
			
			int length = password.length();
			
			int count = 0;
			
			StringBuilder build = new StringBuilder();
			
			for(int i = 0 ; i < length ; i++)
			{
				build.append(++count+""+password.charAt(i));
			}
			
			if(customer!=null)
			{
				if(build.reverse().toString().hashCode()==Integer.valueOf(customer.getPassword()))
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
	
		JSONArray jsonArr = (JSONArray) inventoryMap.get(category);
		
		if(jsonArr==null)
		{
		   jsonArr = new JSONArray();
		}
		
		String data = gson.toJson(inventory);
		
		jsonArr.add(data);
		
		inventoryMap.put(category, jsonArr);

		json.jsonWrite(inventoryMap, "z-kart_db.txt");
		
		return inventoryFormat()+"Inventory Intialized";
	}
	
	@SuppressWarnings("unchecked")
	public String addStocks(Inventory inventory) throws ManualException
	{
		String category = inventory.getCategory();
		
		String brandInventory = inventory.getBrand();
		
		String modelInventory = inventory.getModel();
		
		JSONArray jsonArr =  (JSONArray) inventoryMap.get(category);
		
		if(jsonArr!=null)
		{
			int size = jsonArr.size();
			
			for(int i = 0 ; i < size ; i++)
			{
				String data = (String) jsonArr.get(i);
				
				Inventory invent = gson.fromJson(data, Inventory.class);
				
				String brand = invent.getBrand();
				
				String model = invent.getModel();
				
				if(brand.equalsIgnoreCase(brandInventory) && model.equalsIgnoreCase(modelInventory))
				{
					
					int oldStock = invent.getStock();
					
					invent.setStock((short)(oldStock+inventory.getStock()));
					
					jsonArr.remove(i);
					
					String updatedData = gson.toJson(invent);
					
					jsonArr.add(updatedData);
				}
			}
		}
		
		json.jsonWrite(inventoryMap, "z-kart_db.txt");
		
		return inventoryFormat()+"Inventory Stocks Added";
	}
	
	

	
	public Formatter inventoryFormat()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONArray mobile = (JSONArray) inventoryMap.get("MOBILE");
		
		if(mobile!=null)
		{
			int mobileSize = mobile.size();

			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
				
		if(laptop!=null)
		{
			int laptopSize = laptop.size();

			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
				
		if(tablet!=null)
		{
			int tabletSize = tablet.size();

			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());

			}
		}
		
		return fmt;
	}
	
	public String inventoryFormatWithDiscount()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("\n%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		int count = 0;
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
			
		if(laptop!=null)
		{
			int laptopSize = laptop.size();

			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int discount = inventory.getDiscount();
				
				if(discount!=0)
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray mobile = (JSONArray) inventoryMap.get("MOBILE");
		
		if(mobile!=null)
		{
			int mobileSize = mobile.size();

			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int discount = inventory.getDiscount();
				
				if(discount!=0)
				{
					count++;
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
		
		if(tablet!=null)
		{
			int tabletSize = tablet.size();

			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int discount = inventory.getDiscount();
				
				if(discount!=0)
				{
					count++;
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		if(count == 0)
		{
			return "Today! Seems like non-discount day";
		}
		
		return "--- Items with discount ---\n"+fmt;
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
			if(getArr.contains(item))
			{
				return "Already Added in Cart";
			}
			
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
	

	public String categoryMobile()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONArray mobile = (JSONArray) inventoryMap.get("MOBILE");
				
		int mobileSize = 0;
		
		if(mobile!=null)
		{
			mobileSize = mobile.size();
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		if(mobileSize==0)
		{
			return "Oops! Currently out of stock";
		}
		return "Here We Go\n"+fmt;
	}
	

	public String categoryLaptop()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
		
		int laptopSize = 0;
		
		if(laptop!=null)
		{
			laptopSize = laptop.size();
			
			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		if(laptopSize==0)
		{
			return "Oops! Currently out of stock";
		}
		return "Here We Go\n"+fmt;
	}
	

	public String categoryTablet()
	{
		Formatter fmt = new Formatter();
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");

		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
		
		int tabletSize = 0;
		
		if(tablet!=null)
		{
			tabletSize = tablet.size();
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
						inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
			}
		}
		
		if(tabletSize==0)
		{
			return "Oops! Currently out of stock";
		}
		return "Here We Go\n"+fmt;
	}


	public String searchByBrands(String brand)
	{
		Formatter fmt = new Formatter();
		
		int count = 0;
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		
		JSONArray  mobile = (JSONArray) inventoryMap.get("MOBILE");
		
		int mobileSize = 0;
		
		if(mobile!=null)
		{
			mobileSize = mobile.size();
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getBrand = inventory.getBrand();
				
				if(getBrand.toLowerCase().contains(brand.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),getBrand,inventory.getModel(),
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
		
		int laptopSize = 0;
		
		if(laptop!=null)
		{
			laptopSize = laptop.size();
			
			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getBrand = inventory.getBrand();
				
				if(getBrand.toLowerCase().contains(brand.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),getBrand,inventory.getModel(),
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}

		
		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
		
		int tabletSize = 0;
		
		if(tablet!=null)
		{
			tabletSize = tablet.size();
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String) tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getBrand = inventory.getBrand();
				
				if(getBrand.toLowerCase().contains(brand.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),getBrand,inventory.getModel(),
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		if(count==0)
		{
			return "Oops! "+brand+" is out of stock";
		}
		return fmt+"";
	}

	public String searchByModels(String model)
	{
		Formatter fmt = new Formatter();
		
		int count= 0;
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
		
		int laptopSize = 0;
		
		if(laptop!=null)
		{
			laptopSize = laptop.size();
			
			for(int i = 0 ; i < laptopSize ; i++)
			{
				String data = (String) laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getModel = inventory.getModel();
				
				if(getModel.toLowerCase().contains(model.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),getModel,
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray mobile = (JSONArray) inventoryMap.get("MOBILE");
		
		int mobileSize = 0;
		
		if(mobile!=null)
		{
			mobileSize = mobile.size();
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String) mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getModel = inventory.getModel();
				
				if(getModel.toLowerCase().contains(model.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),getModel,
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
		
		int tabletSize = 0;
		
		if(tablet!=null)
		{
			tabletSize = tablet.size();
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String)tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				String getModel = inventory.getModel();
				
				if(getModel.toLowerCase().contains(model.toLowerCase()))
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),getModel,
							inventory.getPrice(),inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		if(count==0)
		{
			return "Oops! "+model+" is out of stock";
		}
		return fmt+"";
	}
	

	public String searchByPrice(int price)
	{
		Formatter fmt = new Formatter();
		
		int count = 0;
		
		fmt.format("%s %10s %10s %10s %10s %10s\n", "Category","Brand","Model","Price","Stock","Discount(%)");
		
		JSONArray laptop = (JSONArray) inventoryMap.get("LAPTOP");
		
		int laptopSize = 0;
		
		if(laptop!=null)
		{
			laptopSize = laptop.size();
			
			for(int i = 0 ;i < laptopSize ; i++)
			{
				String data = (String)laptop.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int getPrice = inventory.getPrice();
				
				if(getPrice <= price)
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
							getPrice, inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray mobile = (JSONArray) inventoryMap.get("MOBILE");
		
		int mobileSize = 0;
		
		if(mobile!=null)
		{
			mobileSize = mobile.size();
			
			for(int i = 0 ; i < mobileSize ; i++)
			{
				String data = (String)mobile.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int getPrice = inventory.getPrice();
				
				if(getPrice <= price)
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
							getPrice,inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		JSONArray tablet = (JSONArray) inventoryMap.get("TABLET");
		
		int tabletSize = 0;
		
		if(tablet!=null)
		{
			tabletSize = tablet.size();
			
			for(int i = 0 ; i < tabletSize ; i++)
			{
				String data = (String)tablet.get(i);
				
				Inventory inventory = gson.fromJson(data, Inventory.class);
				
				int getPrice = inventory.getPrice();
				
				if(getPrice <= price)
				{
					count++;
					fmt.format("%s %10s %10s %10s %10s %10s\n", inventory.getCategory(),inventory.getBrand(),inventory.getModel(),
							getPrice,inventory.getStock(),inventory.getDiscount());
				}
			}
		}
		
		if(count==0)
		{
			return "Oops! No Products available in "+price+" range";
		}
		return fmt+"";
	}
	
	public String checkOutCart(String userName) throws ManualException
	{
		JSONArray getArr =  (JSONArray) cartMap.get(userName);
		
		if(getArr==null)
		{
			return "Your cart is empty";
		}
		
		int size = getArr.size();
		
		List<String> list = new ArrayList<>();
		
		for(int i = 0 ; i < size ; i++)
		{
			list.add((String) getArr.get(i));
		}
		
		reduceStock(list);
		
		return list+"";
	}
	
	@SuppressWarnings("unchecked")
	public void reduceStock(List<String> list) throws ManualException
	{
		int listSize = list.size();
		
		for(int i = 0 ; i < listSize ; i++)
		{
			String temp = (String) list.get(i);
			
			String[] split = temp.split("-");
			
			String category = split[0];
			
			String brandInventory = split[1];
			
			String modelInventory = split[2];
			
			JSONArray jsonArr =  (JSONArray) inventoryMap.get(category);
			
			if(jsonArr!=null)
			{
				int size = jsonArr.size();
				
				for(int j = 0 ; j < size ; j++)
				{
					String data = (String) jsonArr.get(j);
					
					Inventory invent = gson.fromJson(data, Inventory.class);
					
					String brand = invent.getBrand();
					
					String model = invent.getModel();
					
					if(brand.equalsIgnoreCase(brandInventory) && model.equalsIgnoreCase(modelInventory))
					{
						
						int oldStock = invent.getStock();
						
						invent.setStock((short)(--oldStock));
						
						jsonArr.remove(i);
						
						String updatedData = gson.toJson(invent);
						
						jsonArr.add(updatedData);
					}
					System.out.println(model+" Reduced");
				}
			}
			
			json.jsonWrite(inventoryMap, "z-kart_db.txt");
		}
	}
}
