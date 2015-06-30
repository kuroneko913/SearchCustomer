
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class getGoogleMapJson extends Thread {
	private String key;
	public static ParseJson getjson;
	
	getGoogleMapJson(String key) {
		this.key = key;
	}
	
	public void run() {
		String json;
		
		for(LocationData location : Searching.place.getTowns()) {
			StringBuilder urlStr =
					new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
			urlStr.append("?location=" + location.getLat() + "," + location.getLng());
			urlStr.append("&rankby=distance");
			urlStr.append("&sensor=false");
			urlStr.append("&language=ja");
			urlStr.append("&key=AIzaSyDlKTuwb8qv0AerlaED6uAq4uMtAp-KpYs");
			// 日本語のURLはエンコードしないとHttpURLConnectionで送れない
			try {
				urlStr.append("&keyword=" + URLEncoder.encode(key, "UTF-8"));
			} catch (UnsupportedEncodingException e2) {
				new throwMessage<String>("Parsing json is failed");
			}
			
			do {
				json = Searching.getDatafromHttp(urlStr.toString());
				getjson = new ParseJson(json, location, location.getTown());
				getjson.start();
				try {
					getjson.join();
					
					if(getjson.hasNext() && getjson.getStatus()) {
						urlStr = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=");
						urlStr.append(getjson.getNextkey());
						Thread.sleep(1500);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while(getjson.hasNext() && getjson.getStatus());
			
			if(!getjson.getStatus()) {
				new throwMessage<String>("検索を終了します");
				break;
			}
		} // End of foreach
	}	
}	
