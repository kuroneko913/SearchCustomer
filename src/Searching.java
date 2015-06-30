import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.SwingUtilities;


public class Searching extends Thread {
	private String key, city;
	public static defineType place = new defineType();
	public static List<Customer> customers = new ArrayList<Customer>();
	public static Set<Pair> SearchedShop = new HashSet<Pair>();
	
	Searching(String key, String city) {
		this.key = key;
		this.city = city;
	}
	
	public void run() {

		//API制限すぐくるから節約しましょう 
		
		getGoogleMapJson getjson = new getGoogleMapJson(key);
		getjson.start();
		try {
			getjson.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		/* 出力変数、ストリーム */
		String outputfilename = this.city + "_" + this.key + ".csv";
		File outdir = new File("./");
		File outputfile = new File(outdir, outputfilename);
		PrintWriter output;
		try {
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile), "UTF-8")));
			new throwMessage<String>("Output Ready !");
			int cnt = 1;
			for(Customer customer : customers) {
				System.out.println(customer.Output(cnt));
				output.write(customer.Output(cnt++));
			} 
			output.close();
			new throwMessage<String>("Output Successfully !");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					SearchString.getFindButton().setEnabled(true);
				}
			});
			
		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	

	static String getDatafromHttp(String url) {
		
		String line;
		BufferedReader input;
		StringBuilder data = new StringBuilder();
		
		try {
			/** httpにコネクション張って通信する */
			URL site = new URL(url);
			HttpURLConnection http;
			try {
				http = (HttpURLConnection) site.openConnection();
				http.setRequestMethod("GET");
				http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				http.connect();
				
				input = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
				while((line = input.readLine()) != null) {
					data.append(line + '\n');
				}
				
				return data.toString();
				
			} catch (IOException e) {
				new throwMessage<String>("Cannot open the URL: " + url);
				return "";
			}
		} catch (MalformedURLException e) {
			new throwMessage<String>("URL is incorrect -> " + url);
			return "";
		}
	}
}
