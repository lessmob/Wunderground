import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GetYesterdaysWeather {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject json;
		String apiKey = "xxxxxxxxxxxxxxxx";//Put your wunderground.com API Key here
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1;

		String state="xx";//Put your 2 letter state code here. Ex: state="FL";
		String city="xxxxxxxxxxx";//Put your city here. Ex: state="Tallahassee";
		
		String yesterdaysDate = ""+year+checkDigit(month)+checkDigit(day);
		String url = "http://api.wunderground.com/api/"+apiKey+"/history_"+yesterdaysDate+"/q/"+state+"/"+city+".json";
		System.out.println(url);
		try {
			json = readJsonFromUrl(url);
			System.out.println(json.toString());
			JSONObject obj = json.getJSONObject("history");
			JSONArray array = obj.getJSONArray("observations");
			for(int i=0;i<array.length();i++){
				JSONObject o = array.getJSONObject(i);
				String tempi = o.getString("tempi");
				JSONObject n = o.getJSONObject("utcdate");
				String hour = n.getString("hour");
				String min = n.getString("min");
				System.out.println("Temp: "+tempi+" @ "+hour+":"+min);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String checkDigit(int value){
		String newValue = "";
		if(value<10){
			newValue = "0"+value;
		}else{
			newValue = value+"";
		}
		return newValue;
	}
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	

}
