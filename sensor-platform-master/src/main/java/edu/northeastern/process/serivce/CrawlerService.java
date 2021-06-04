package edu.northeastern.process.serivce;


import com.github.houbbbbb.crawlerspringbootstarter.crframe.properties.CrawlerProperties;
import com.github.houbbbbb.crawlerspringbootstarter.crframe.webcrawler.Starter;
import com.github.houbbbbb.crawlerspringbootstarter.crframe.webcrawler.WebCrawler;
import edu.northeastern.process.dao.CrawlerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService {

    @Autowired
    CrawlerRepo crawlerRepo;

    @Value("${logging.file.name}")
    private String logfile;

    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    // TODO: Web Crawler
    public void startProcess() {
        CrawlerProperties crawlerProperties = new CrawlerProperties();
        WebCrawler webCrawler = new WebCrawler(crawlerProperties);
        Starter starter = webCrawler.getStarter();
        String url = "http://www..com/";

        starter.setRootUrl(url);
        starter.setParser((document, tran) -> {

        });
        starter.start();

    }

}
