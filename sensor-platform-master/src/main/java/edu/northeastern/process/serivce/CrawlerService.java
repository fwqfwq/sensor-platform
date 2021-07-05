package edu.northeastern.process.serivce;


import edu.northeastern.process.beans.CrawlerEntity;
import edu.northeastern.process.dao.CrawlerRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CrawlerService {

    @Autowired
    CrawlerRepo crawlerRepo;

    @Value("${logging.file.name}")
    private String logfile;

    // url for crawler
    @Value("${crawler.url}")
    private String url;


    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    public void demoProcess() throws IOException {
        logger.info(">>>> Starting crawl process... ");

        // Demo Crawler Process from 'rotten tomato'
        Document doc = Jsoup.connect(url).get();

        String className = "div.col-sm-8";
        Elements contents = doc.select(className);


        logger.info(">>>> processing... ");
        for(Element content: contents){
            CrawlerEntity crawlerEntity = new CrawlerEntity();

            crawlerEntity.setTitle(content.select("p[class]").first().text());
            crawlerEntity.setDate(content.select("p[class]").next().text());
            crawlerEntity.setUrl(content.select("a").attr("href"));
            crawlerEntity.setImgUrl(content.select("img").attr("src"));

            crawlerRepo.save(crawlerEntity);
        }
        logger.info(">>>> done");
    }



    public boolean isExist(String id){
        CrawlerEntity crawlerEntity = getById(id);
        return null != crawlerEntity;
    }

    public CrawlerEntity getById(String id) {
        return crawlerRepo.findById(id);
    }

    public void add(CrawlerEntity crawlerEntity) {
        crawlerRepo.save(crawlerEntity);
    }

}
