
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

class defineType {
	private Set<String> citys; 
	private Set<LocationData> towns;
	
	defineType() {
		this.citys = new HashSet<String>();
		this.towns = new HashSet<LocationData>();
	}
	
	Set<String> getCitys() {
		return this.citys;
	}
	
	Set<LocationData> getTowns() {
		return this.towns;
	}
}

class PlaceholderFocusListener implements FocusListener {
	  private static final Color INACTIVE
	    = UIManager.getColor("TextField.inactiveForeground");
	  private final String hintMessage;
	  
	  PlaceholderFocusListener(JTextComponent tf) {
	    hintMessage = tf.getText();
	    tf.setForeground(INACTIVE);
	  }
	  
	  @Override 
	  public void focusGained(FocusEvent e) {
	    JTextComponent tf = (JTextComponent) e.getComponent();
	    if (hintMessage.equals(tf.getText())
	        && INACTIVE.equals(tf.getForeground())) {
	      tf.setForeground(UIManager.getColor("TextField.foreground"));
	      tf.setText("");
	    }
	  }
	  
	  @Override 
	  public void focusLost(FocusEvent e) {
	    JTextComponent tf = (JTextComponent) e.getComponent();
	    if ("".equals(tf.getText().trim())) {
	      tf.setForeground(INACTIVE);
	      tf.setText(hintMessage);
	    }
	  }
	}

class Pair {
	private String first, second;
	
	Pair(String first, String second) {
		this.first = first;
		this.second = second;
	}
	
	public boolean equals(Pair a, Pair b) {
		if(a.first == b.first && a.second == b.second) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getFirst() {
		return this.first;
	}
	
	public String getSecond() {
		return this.second;
	}
}

class Customer {
	private String name, zip, address, tel, fax, mail, hp, lat, lng;
	
	Customer() {

	}
	
	Customer(String name, String zip, String address, String tel, String fax, String mail, String hp, String lat, String lng) {
		this.name = name;
		this.zip = zip;
		this.address = address;
		this.tel = tel;
		this.fax = fax;
		this.mail = mail;
		this.hp = hp;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getZip() {
		return this.zip;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getTel() {
		return this.tel;
	}
	
	public String getFax() {
		return this.fax;
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public String getHp() {
		return this.hp;
	}
	
	public String getLat() {
		return this.lat;
	}
	
	public String getLng() {
		return this.lng;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setHp(String hp) {
		this.hp = hp;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String Output(int idx) {
		return "\"" + String.valueOf(idx) + "\",\"" + this.name + "\",\"" + this.address + "\",\"" + this.zip +
				"\",\"" + this.tel + "\",\"" + this.fax + "\",\"" + this.mail + "\",\"" + 
				this.hp + "\",\"" + this.lat + "\",\"" + this.lng + "\"" + '\n'; 
	}
}

class LocationData { 
	private String city, town, post;
	private Double lat, lng;
	
	LocationData(String town) {
		this.town = town;
	}
	
	LocationData(String city, String town, Double lat, Double lng, String post) {
		setCity(city);
		setTown(town);
		setLat(lat);
		setLng(lng);
		setPost(post);
	}

	String getCity() {
		return this.city;
	}
	
	String getTown() {
		return this.town;
	}
	
	Double getLat() {
		return this.lat;
	}
	
	Double getLng() {
		return this.lng;
	}
	
	String getPost() {
		return this.post;
	}
	
	void setCity(String city) {
		this.city = city;
	}
	
	void setTown(String town) {
		this.town = town;
	}
	
	void setLat(Double lat) {
		this.lat = lat;
	}
	
	void setLng(Double lng) {
		this.lng = lng;
	}
	
	void setPost(String post) {
		this.post = post;
	}
	
	void output() {
		System.out.println(getCity() + getTown());
		System.out.println("lat = " + getLat() + ", lng = " + getLng());
		System.out.println("Post: " + getPost());
	}
}