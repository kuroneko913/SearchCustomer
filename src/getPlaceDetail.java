import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;


public class getPlaceDetail {
	private Customer customer = new Customer();
	
	getPlaceDetail(String placeID) {
		
		StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
		
		url.append("placeid=" + placeID);
		url.append("&language=ja");
		url.append("&key=AIzaSyDlKTuwb8qv0AerlaED6uAq4uMtAp-KpYs");
		
		//System.out.println(url.toString());
		String detail = Searching.getDatafromHttp(url.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			Map<?, ?> rootMap = mapper.readValue(detail, Map.class);
			Map<?, ?> results = (Map<?, ?>)rootMap.get("result");
			//String vicinity = (String)results.get("vicinity");
			String address = (String)results.get("formatted_address");
			String phone = (String)results.get("formatted_phone_number");
			String hp = (String)results.get("website");
			
			customer.setHp(hp);
			customer.setTel(phone);
			customer.setAddress(getAddress(address));
			
		} catch (IOException e) {
			new throwMessage<String>("String parsing json is failed");
		}
	}
	
	public Customer getValue() {
		return customer;
	}

	private static String getAddress(String str) {
		 String addressFormat = "((–kŠC“¹|“Œ‹“s|(‘åã|‹“s)•{|(_“Şì|˜a‰ÌR|­™“‡)Œ§|[^\\w\\d]{2}Œ§)[^\\w\\d]{1,6}[sŒS‹æ’¬‘º][^\\w\\d]{1,20}[\\d‚O-‚XZˆê-‹ã\ã‰º“Œ¼]{1,20}+[^\\s]*)";
		 Pattern pattern = Pattern.compile(addressFormat);
		 Matcher matcher = pattern.matcher(str);
		 
		 if(matcher.find()) {
			 return matcher.group(0);
		 } else {
			 return "";
		 }
	}
}
