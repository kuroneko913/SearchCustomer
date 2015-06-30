
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class getCustomerInfo extends Thread {
	private boolean isRunning = true;
	private String shopName, key;
	private Set<String> Mail = new HashSet<String>();
	private Set<String> Zip = new HashSet<String>();
	
	getCustomerInfo(String shopName, String key) {
		this.shopName = shopName;
		this.key = key;
	}
	
	public String getMail() {
		if(Mail.isEmpty()) return "";
		else return Mail.iterator().next();
	}
	
	public String getZip() {
		if(Zip.isEmpty()) return "";
		else return Zip.iterator().next();
	}
	
	public void stopThread() {
		this.isRunning = false;
	}
	
	public boolean getRunningStatus() {
		return this.isRunning;
	}

	public void run() {
		
		StringBuilder strurl = new StringBuilder("http://www.google.jp/search?q=");
		
		try {
			//strurl.append(URLEncoder.encode(key + " ", "UTF-8"));
			strurl.append(URLEncoder.encode(this.shopName + " " + this.key + " -amazon -goo -maps", "UTF-8"));
			strurl.append("&num=10");
		} catch (UnsupportedEncodingException e2) {
			System.out.println("Encoded by UTF-8 has failed");
		}
			
		Mail.clear();
		List<String> urls = GoogleSearchResURL(strurl.toString());
		if(urls.isEmpty()) {
			stopThread();
		} else {
			for(String url : urls) {
				String text = Searching.getDatafromHttp(url);
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					System.out.println("Interrupt of higher priority process occurs!");
				}
					
				System.out.println("URL: " + url);
				text = text.replaceAll("[\\s\\t]", "").replace("\n", " ");
				//System.out.println(text);
				
				Mail.addAll(URLCrawler.getMailAddress(text));
			}
		}
			
		/** Use Crawler4j */ 
		//new CrawlerController(urls, shopName);
		System.out.println("finished!!!!!!!!!!!!!!!!!!!!");
	}
	
	private List<String> GoogleSearchResURL(String url) {
		
		List<String> ret = new ArrayList<String>();
		
		try {
			Document doc = Jsoup.connect(url).userAgent("Mozilla").ignoreHttpErrors(true).timeout(5000).get();
			Elements links = doc.select("li.g");
			
			for(Element link : links) {
				Elements titles = link.select("h3[class=r]");
				String returl = parseURL(titles.get(0).getElementsByTag("a").attr("href"));
				ret.add(returl);
				//System.out.println(returl);
			}
		
		} catch (IOException e1) {
			System.out.println("Jsoup Error");
		}
		return ret;
	}

	private String parseURL(String url) {
		url = url.replace("/url?q=", "");
		url = url.split("&")[0];
		return url;
	}
}