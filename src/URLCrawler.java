
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class URLCrawler extends WebCrawler{
	 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
             + "|png|mp3|mp3|zip|gz))$");

	 /**
	  * This method receives two parameters. The first parameter is the page
	  * in which we have discovered this new url and the second parameter is
	  * the new url. You should implement this function to specify whether
	  * the given url should be crawled or not (based on your crawling logic).
	  * In this example, we are instructing the crawler to ignore urls that
	  * have css, js, git, ... extensions. 
	  * In this case, we didn't need the referringPage parameter to make the decision.
	  */
	 public boolean shouldVisit(Page referringPage, WebURL url) {
		 String href = url.getURL().toLowerCase();
		 return !FILTERS.matcher(href).matches();
	 }						

	 /**
	  * This function is called when a page is fetched and ready
	  * to be processed by your program.
	  */
	 public void visit(Page page) {
		 String url = page.getWebURL().getURL();
		 System.out.println("URL: " + url);
		 
		 if(page.getParseData() instanceof HtmlParseData) {
			 HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			 String text = htmlParseData.getText();
			 
			 text = text.replaceAll("[\\s\\t]", "").replace("\n", " ");
			 
			/* CrawlerController.Address.addAll(getAddress(text));
			 CrawlerController.Mail.addAll(getMailAddress(text));
			 CrawlerController.Phone.addAll(getPhoneNumber(text));
			 CrawlerController.Zip.addAll(getZip(text));
			 */
			 //System.out.println(text);
			 //System.out.println(html);
		}
	 }
	 
	 /* ZŠ”²‚«o‚µ */
	 public static ArrayList<String> getAddress(String html) {
		 String addressFormat = "((–kŠC“¹|“Œ‹“s|(‘åã|‹“s)•{|(_“Şì|˜a‰ÌR|­™“‡)Œ§|[^\\s\\w\\d@]{2}Œ§)[^\\s\\w\\d@]{1,6}[sŒS‹æ’¬‘º][^\\s\\w\\d@]{1,20}[\\d‚O-‚XZˆê-]{1,20})";
		 Pattern pattern = Pattern.compile(addressFormat);
		 Matcher matcher = pattern.matcher(html);
		 ArrayList<String> ret = new ArrayList<String>();
		 
		 while(matcher.find()) {
			ret.add(matcher.group(0));
		 }
		 return new ArrayList<String>(new HashSet<String>(ret)); 
	 }
	 
	 /* ƒ[ƒ‹ƒAƒhƒŒƒX”²‚«o‚µ */
	 public static ArrayList<String> getMailAddress(String html) {
			
		 String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
		 Pattern pattern = Pattern.compile(mailFormat);
		 Matcher matcher = pattern.matcher(html);
		 ArrayList<String> ret = new ArrayList<String>();
		 
		 while(matcher.find()) {
			 ret.add(matcher.group(0));
		 }
		 return new ArrayList<String>(new HashSet<String>(ret)); 
	 }
	 
	 /* “d˜b”Ô†”²‚«o‚µ */
	 public static ArrayList<String> getPhoneNumber(String html) {
		 String phoneFormat = "\\d{1,4}-\\d{4}$|\\d{2,5}-\\d{1,4}-\\d{4}";
		 Pattern pattern = Pattern.compile(phoneFormat);
		 Matcher matcher = pattern.matcher(html);
		 ArrayList<String> ret = new ArrayList<String>();
		 
		 while(matcher.find()) {
			ret.add(matcher.group(0));
		 }
		 return new ArrayList<String>(new HashSet<String>(ret)); 
	 }
	 
	 /* —X•Ö”Ô†”²‚«o‚µ */
	 public static ArrayList<String> getZip(String html) {
		 String zipFormat = "(?<!(\\d|-))\\d{3}-\\d{4}(?!(\\d|-))";
		 Pattern pattern = Pattern.compile(zipFormat);
		 Matcher matcher = pattern.matcher(html);
		 ArrayList<String> ret = new ArrayList<String>();
		 
		 while(matcher.find()) {
			ret.add(matcher.group(0));
		 }
		 return new ArrayList<String>(new HashSet<String>(ret)); 
	 }
}