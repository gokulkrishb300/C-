package json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import manualexception.ManualException;

public class Json {
	
	public void jsonWrite(JSONObject jsonObj , String fileName) throws ManualException
	{
		try(FileWriter writer = new FileWriter(fileName);)
		{
			writer.write(jsonObj.toJSONString());
		}
		catch(IOException io)
		{
			throw new ManualException("File Writing Failed for "+fileName);
		}
	}
	
	public JSONObject jsonRead(String fileName) throws ManualException
	{
		try(FileReader reader = new FileReader(fileName);)
		{
			JSONParser parser = new JSONParser();
			
			JSONObject jsonObj = (JSONObject)parser.parse(reader);
			
			return jsonObj;
		}
		catch(ParseException pe)
		{
			throw new ManualException("File Parsing Failed for "+fileName);
		}
		catch(IOException io)
		{
			throw new ManualException("File Reading Failed for "+fileName);
		}

		
	}
}
