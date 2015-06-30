import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class CrawlerController {
 	
	CrawlerController(List<String> seedURL, String shop) {
        String crawlStorageFolder = "./temp/";		// クロールした中間データの保存先
        int numberOfCrawlers = 1;							

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(200);						//　サーバーにアクセスする間隔を最低でも200msに
        config.setMaxDepthOfCrawling(0);
        config.setMaxPagesToFetch(10);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = null;
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
		} catch (Exception e) {
			e.printStackTrace();
		}

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
		for(String url : seedURL) { 
			//System.out.println(url);
			controller.addSeed(url);
		}	

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(URLCrawler.class, numberOfCrawlers);
        
       // makeSearchString.place.customers.add(new Customer(shop, (Zip.isEmpty()?"":Zip.get(0)), (Address.isEmpty()?"":Address.get(0)), (Phone.isEmpty()?"":Phone.get(0)), "", (Mail.isEmpty()?"":Mail.get(0)), "", "", ""));
       // Zip.clear(); Address.clear(); Phone.clear(); Mail.clear(); 
	}
}