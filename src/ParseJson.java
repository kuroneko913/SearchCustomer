
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParseJson extends Thread {
	private LocationData location;
	private String json, nextKey, key;
	private boolean Status;
	private int miss;
	private static int count = 1;
	
	ParseJson(String json, LocationData location, String key) {
		this.location = location;
		this.key = key;
		this.json = json;
		this.Status = true;
		this.miss = 0;
		this.nextKey = null;
	}
	
	public static void setCount(int st) {
		count = st;
	}
	
	public void setStatus(boolean stat) {
		this.Status = stat;
	}
	
	public boolean getStatus() {
		return this.Status;
	}
	
	public boolean hasNext() {
		return this.nextKey != null && this.miss < 3;
	}
	
	public String getNextkey() {
		return this.nextKey;
	}

	public void run() {
		
		/* Error */
		String foo = this.key;
		foo.replaceAll("", "");
		/* jsonパース用 */
		Map<?, ?> rootMap = null;
		ObjectMapper mapper = new ObjectMapper();	
		try {
			rootMap = mapper.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ここでパース
		this.nextKey = (String)rootMap.get("next_page_token");
		ArrayList<?> nextArray = (ArrayList<?>)rootMap.get("results");
		for(int i = 0; i < nextArray.size(); i++) {
			Customer customer = new Customer();
			Map<?, ?> thirdMap = (Map<?, ?>)nextArray.get(i);
			Map<?, ?> fourthMap = (Map<?, ?>)((Map<?, ?>) thirdMap.get("geometry")).get("location");
			Double lat = (Double)fourthMap.get("lat");
			Double lng = (Double)fourthMap.get("lng");
			String name = (String)thirdMap.get("name");
			String vicinity = (String)thirdMap.get("vicinity");
			
			if(!this.Status) {
				break;
			}
			
			if(vicinity.contains(location.getTown()) && !Searching.SearchedShop.contains(new Pair(name, vicinity))) {
				
				new throwMessage<String>(String.valueOf(count++) + "件目を処理中...");
				getPlaceDetail getDetail = new getPlaceDetail((String)thirdMap.get("place_id"));
				customer = getDetail.getValue();
				customer.setName(name);
				customer.setLat(String.valueOf(lat));
				customer.setLng(String.valueOf(lng));
				customer.setZip(String.valueOf(location.getPost()));
				
				Searching.SearchedShop.add(new Pair(name, vicinity));
				
				/*getCustomerInfo getCustomer = new getCustomerInfo(name, key);
				getCustomer.start();
				try {
					getCustomer.join();
				} catch (InterruptedException e) {
					System.out.println("Interrupt of higher priority process occurs!");
				}
				if(!getCustomer.getRunningStatus()) {
					this.Status = false;
					break Exit;
				} */
				Searching.customers.add(customer);
			} 
			else if(++this.miss > 5) break;
			
		}
	}
}
