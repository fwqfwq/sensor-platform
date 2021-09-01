package edu.northeastern.process.serivce;


import edu.northeastern.base.manager.ActorManager;
import edu.northeastern.base.manager.AlertSensor;
import edu.northeastern.base.manager.SensorManager;
import edu.northeastern.process.beans.CrawlerEntity;
import edu.northeastern.process.dao.CrawlerRepo;
import edu.northeastern.process.sensors.CrawlerSensor;
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
import java.util.Random;
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

    private String KEY = "crawler";
    private String ID = "1";


    private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);

    /*
    Listen message to trigger crawler.
    ! Need to customized html parsing service.

    Crawler Process procedures:
        1. Simulate message sender by do random int (random number from 0-10)
        2. if number == 5, do crawler 1
        3. if number == 7, do crawler 2

    Sensors function:
        4. Listen database table, if content updated give alert
    */

    /* Need to customized */
    private void crawlPage(){ }

    // Demo crawler process 1 from website rotten tomato
    private void crawlPageDemo1(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String className = "div.col-sm-8";
        Elements contents = doc.select(className);

        logger.info(">>>> processing crawler 1... ");

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

    // Demo crawler process 2 from website imdb
    private void crawlPageDemo2(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String className = "li.ipl-simple-list__item";
        Elements contents = doc.select(className);

        logger.info(">>>> processing crawler 2... ");

        for(Element content: contents){
            CrawlerEntity crawlerEntity = new CrawlerEntity();

            crawlerEntity.setTitle(content.select(".compact-news-item").text());
            crawlerEntity.setDate(content.select("li.compact-news-item__date").text());
            crawlerEntity.setUrl(content.select("a").attr("href"));

            add(crawlerEntity);
        }
        logger.info(">>>> done");
    }



    public void startCrawler(String url1, String url2) {
        logger.info(">>>> starting crawl process... ");

        // Initiate crawler sensor and listen func
        initSensor(KEY, ID);
        listenTable();

        try {
            Thread.sleep(5000);

            for (int i = 0; i < 200; i++) {
                int random = new Random().nextInt(10);
                logger.info(">>> Signal is " + random);
                if (random == 5) {
                    // Call crawler func
                    crawlPageDemo1(url1);
                    sensorAlert("No.1 ");
                }
                else if(random == 6) {
                    // Call crawler func
                    crawlPageDemo2(url2);
                    sensorAlert("No.2 ");
                }
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        // Stop sensor
        removeSensor(KEY);
    }

    public void add(CrawlerEntity crawlerEntity) {
        crawlerRepo.save(crawlerEntity);
    }

    private void initSensor(String key, String id) {
        ActorManager.getSensorManager().tell(new SensorManager.Add(key, CrawlerSensor.create(id)));
    }

    private void listenTable() {
        ActorManager.getSensorManager().tell(new SensorManager.Schedule(
                "crawlerDbDemo",
                KEY,
                new CrawlerSensor.Query(crawlerRepo.findById(Integer.parseInt(ID))),
                "check database every 5 sec",
                "*/5 * * ? * *",
                null,
                TimeZone.getDefault()
        ));
    }

    private void sensorAlert(String msg) {
        ActorManager.getAlertSensor().tell(new AlertSensor.Alert(msg + "crawler invoked. "));
    }

    private void removeSensor(String key) {
        ActorManager.getSensorManager().tell(new SensorManager.Stop(key));
    }

}
