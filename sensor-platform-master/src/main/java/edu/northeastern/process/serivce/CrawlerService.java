package edu.northeastern.process.serivce;


import edu.northeastern.base.condition.CrawlerCondition;
import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.beans.CrawlerEntity;
import edu.northeastern.process.dao.CrawlerRepo;
import edu.northeastern.process.sensors.CrawlerSensor;
import edu.northeastern.process.sensors.DatabaseSensor;
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
import java.util.TimeZone;

/**
 * Created by F Wu
 */

@Service
public class CrawlerService {

    @Autowired
    CrawlerRepo crawlerRepo;

    @Value("${logging.file.name}")
    private String logfile;


    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    /*
    Crawler process procedures:
        1. Crawl from websites, store data into db
        2. Repeat Crawler every 5 min
        3. But only store updated data into db

    Sensors function:
        4. Listen log, if content updated give alert
        5. Parse the content, if negative give alert
        6. If run crawler 100 times give alert
    */

    public void startCrawler(String url) throws IOException {
        logger.info(">>>> starting crawl process... ");

        // Initiate Crawler Sensor
        initSensor("crawler", url);
        listenTable();

        // TODO


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Demo crawler process from website 'rotten tomato', using JSoup
        Document doc = Jsoup.connect(url).get();

        String className = "div.col-sm-8";
        Elements contents = doc.select(className);


        logger.info(">>>> processing... ");

        // Parsing here should be customized..
        for(Element content: contents){
            CrawlerEntity crawlerEntity = new CrawlerEntity();

            crawlerEntity.setTitle(content.select("p[class]").first().text());
            crawlerEntity.setDate(content.select("p[class]").next().text());
            crawlerEntity.setUrl(content.select("a").attr("href"));
            crawlerEntity.setImgUrl(content.select("img").attr("src"));

            add(crawlerEntity);
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

    private void initSensor(String key, String url) {
        ActorManager.getSensorManager().tell(new SensorManager.Add(key, CrawlerSensor.create(url)));
    }

    private void listenTable() {
        ActorManager.getSensorManager().tell(new SensorManager.Schedule(
                "crawlerDemo",
                "crawler",
                new CrawlerSensor.Update(crawlerRepo.findById("crawler")),
                "do crawler every 5 min",
                "0 */5 * ? * *",
                null,
                TimeZone.getDefault()
        ));
    }



}
