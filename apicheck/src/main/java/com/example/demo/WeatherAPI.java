package com.example.demo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class WeatherAPI {
	
	
	public WeatherAPI() {
	super();
	// TODO Auto-generated constructor stub
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Logger logger=Logger.getLogger(WeatherAPI.class.getName());
			boolean append=true;
			FileHandler fileHandler=new FileHandler("C:/data/tmp/default.log",append);
			logger.addHandler(fileHandler);
			//logger.addHandler(new ConsoleHandler());
			
			//logger.log(Level.INFO, "message");
			//logger.log(Level.ALL, "message");
			logger.info("Example log from console");
			//logger.setUseParentHandlers(false);
			
			String inline = "";
			URL url=new URL("https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			//Use the connect method to create the connection bridge
			conn.connect();
			//Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			logger.info("Response code is: " +responsecode);
			
			//Iterate condition to if response code is not 200 then throw a runtime exception
			//else continue the actual process of getting the JSON data
			if(responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " +responsecode);
			else
			{
				//Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				
				sc.close();
			}
			
			JSONParser parser =new JSONParser();
			JSONObject jsonobj = (JSONObject)parser.parse(inline); 
			
			logger.info("JSONObject:"+jsonobj);
			JSONArray jsonarr = (JSONArray) jsonobj.get("list");
			logger.info("Listobject:"+jsonarr);
			logger.info("Listobject size:"+jsonarr.size());
			
			List<String>cityNames=new ArrayList<>();
			String jsonarr_1=null;
			for(int i=0;i<jsonarr.size();i++) {
				JSONObject jsonobj_1 = (JSONObject)jsonarr.get(i);
				//Store the JSON object in JSON array as objects (For level 2 array element i.e names Components)
				 jsonarr_1 = (String) jsonobj_1.get("name");
				 cityNames.add(jsonarr_1);
				
			}
			logger.info("Listobject_cityNames:"+cityNames);
			
			logger.info("Count of cityNames starting with letter 'T': "+cityNames.stream().filter(x->x.startsWith("T")).count());
				
			
		}

		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
