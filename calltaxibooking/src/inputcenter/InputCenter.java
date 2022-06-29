package inputcenter;



import java.util.*;

public class InputCenter {
	Scanner scan = new Scanner(System.in);
	
	public int getInt(String input) 
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		
		try
		{
			if(!(value == null) && !value.isEmpty())
			{
				return Integer.valueOf(value);
			}
		}
		catch(NumberFormatException e)
		{
		    System.out.println("Integer input only");
		}
		return getInt(input);
	}
	
	public float getFloat(String input)
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		
		try
		{
			if(!(value == null) && !value.isEmpty())
			{
				return Float.valueOf(value);
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("Float input only");
		}
		return getFloat(input);
	}
	
	public char getChar(String input) 
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		
		try
		{
			if(!(value == null) && !value.isEmpty() && value.length()==1)
			{
				return value.charAt(0);
			}
		}
		catch(InputMismatchException e)
		{
			System.out.println("Char input only");
		}
		return getChar(input);
	}
	
	public String getString(String input) 
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		

			if(!(value == null) && !value.isEmpty())
			{
				return value;
			}

		return getString(input);
	}
	
	public long getLong(String input) 
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		
		try
		{
			if(!(value == null) && !value.isEmpty())
			{
				return Long.valueOf(value);
			}
		}
		catch(NumberFormatException e)
		{	
			System.out.println("Long input only");
		}
		return getLong(input);
	}
	
	public boolean getBoolean(String input)
	{
		System.out.print(input);
		
		String value = scan.nextLine();
		
		try
		{
			if(!(value == null) && !value.isEmpty())
			{
				return Boolean.valueOf(value);
			}
		}
		catch(Exception e)
		{
			System.out.println("Boolean input only"+e);
		}
		return getBoolean(input);
	}
	
}
