
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class makeSearchString extends Thread {
	private String pref, city;
	private boolean status;
	
	makeSearchString(String pref, String city) {
		this.pref = pref;
		this.city = city;
		this.status = true;
	}
	
	makeSearchString(String pref, String city, String town) {
		this.pref = pref;
		this.city = city;
		this.status = true;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	public void run() {
		
		if(city.length() == 0) { // 市が指定されていない
			String location = pref, json;
			StringBuilder strurl = new StringBuilder("http://geoapi.heartrails.com/api/json?method=getCities");
			
			try {
				strurl.append("&prefecture=" + URLEncoder.encode(location, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				new throwMessage<String>("Encoded by UTF-8 has failed");
			}
				
			json = Searching.getDatafromHttp(strurl.toString());
			Map<?, ?> rootMap = null;
			ObjectMapper mapper = new ObjectMapper();
			try {
				rootMap = mapper.readValue(json.toString(), Map.class);
				ArrayList<?> thirdList = (ArrayList<?>)((Map<?, ?>)rootMap.get("response")).get("location");
				if(thirdList != null) {
					for(int i = 0; i < thirdList.size(); i++) {
						String city = (String)((Map<?, ?>)thirdList.get(i)).get("city");
						Searching.place.getCitys().add(city);
					}
				} else {
					new throwMessage<String>("指定された地名が存在しません。");
					this.status = false;
					return ;
				}
			} catch (IOException e) {
				new throwMessage<String>("String parsing json is failed");
			}
		} else {
			Searching.place.getCitys().add(city);
		}
			
		for(String location : Searching.place.getCitys()) { 
			String json;
			StringBuilder strurl = new StringBuilder("http://geoapi.heartrails.com/api/json?method=getTowns");
			
			try {
				strurl.append("&city=" + URLEncoder.encode(location, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				new throwMessage<String>("Encoded by UTF-8 has failed");
			}
			
			json = Searching.getDatafromHttp(strurl.toString());
			Map<?, ?> rootMap = null;
			ObjectMapper mapper = new ObjectMapper();
			try {
				rootMap = mapper.readValue(json.toString(), Map.class);
				ArrayList<?> thirdList = (ArrayList<?>)((Map<?, ?>)rootMap.get("response")).get("location");
				if(thirdList != null) {
					for(int i = 0; i < thirdList.size(); i++) {
						String city = (String)((Map<?, ?>)thirdList.get(i)).get("city");
						String town = (String)((Map<?, ?>)thirdList.get(i)).get("town");
						Double lat = Double.parseDouble((String)((Map<?, ?>)thirdList.get(i)).get("y"));
						Double lng = Double.parseDouble((String)((Map<?, ?>)thirdList.get(i)).get("x"));
						String post = (String)((Map<?, ?>)thirdList.get(i)).get("postal");
						
						post = post.substring(0, 3) + "-" + post.substring(3, 7);
						town = town.replaceAll("町$", "");
	
						if(town.equals("以下に掲載がない場合")) town = null;
						Searching.place.getTowns().add(new LocationData(city, town, lat, lng, post));
					}
				} else {
					new throwMessage<String>("指定された地名が存在しません。");
					this.status = false;
					return ;
				}
			} catch (IOException e) {
				new throwMessage<String>("Parsing json is failed");
			}
		}
	}
}