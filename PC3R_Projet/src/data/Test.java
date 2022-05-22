package data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import simulation.Market;

public class Test {
	public static void main(String[] args) throws ParseException, IOException, InterruptedException {
//		HttpRequest request = HttpRequest.newBuilder()
//			.uri(URI.create("https://yfapi.net/v6/finance/quote?region=US&lang=en&symbols=AAPL%2CFB%2CTSLA"))
//			.header("accept", "application/json")
//			.header("x-api-key", "shqz7Hpy8z2UW6eJXWMly3nl8yw5TEmWLrStHzL8")
//			.method("GET", HttpRequest.BodyPublishers.noBody())
//			.build();
//		
//		HttpResponse<String> response = HttpClient.newHttpClient()
//			.send(request, HttpResponse.BodyHandlers.ofString());
//		
////		System.out.println(response.body());
//		
//		//Using the JSON simple library parse the string into a json object
//	    JSONParser parse = new JSONParser();
//	    JSONObject data_obj = (JSONObject) parse.parse(response.body());
//
//	    //Get the required object from the above created object
//	    JSONObject obj = (JSONObject) data_obj.get("quoteResponse");
//	    
//	    JSONArray result = (JSONArray) obj.get("result");
//	    
//	    for (int i = 0; i < result.size(); i++) {
//	    	JSONObject data = (JSONObject) result.get(i);
//	    	System.out.println(data.get("regularMarketPrice"));
//	    }
//
//	    //Get the required data using its key
////	    System.out.println(((JSONObject) result.get(0)).get("regularMarketPrice"));
		
		Market m = new Market();
		m.updatePrice();
		System.out.println("bread : " + m.price(Resource.bread));
		System.out.println("car : " + m.price(Resource.car));
		System.out.println("phone : " + m.price(Resource.phone));
	}
}
