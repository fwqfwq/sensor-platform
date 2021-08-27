package edu.northeastern.process.controller;


import edu.northeastern.process.reqres.EventRequest;
import edu.northeastern.process.serivce.CrawlerService;
import edu.northeastern.process.serivce.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by F Wu
 */

@RestController
public class CrawlerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private EventService eventService;

    @Value("${crawler.table.name}")
    private String tableName;

    // Demo url for crawler
    @Value("${crawler.url}")
    private String url;


    @RequestMapping(value = "/crawler", method = RequestMethod.GET)
    public ResponseEntity<String> crawlerProcess() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        return ResponseEntity.ok().body(">>>> Crawler Process....");
    }


    // Demo url
    @RequestMapping(value = "/crawler/demo", method = RequestMethod.POST)
    public ResponseEntity<String> crawlerDemo(@RequestBody EventRequest req) throws IOException, InterruptedException {

        crawlerService.startCrawler(url);
        eventService.createDatabaseEvent(req);

        String sql = "select * from " + tableName;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);

        return ResponseEntity.ok()
                .body(list.toString());
    }


    @RequestMapping(value = "/crawler/add", method = RequestMethod.POST)
    public String createEvent(@RequestBody EventRequest req) {




        return eventService.createDatabaseEvent(req);
    }


    @RequestMapping(value = "/crawler/delete/{crawlerID}", method = RequestMethod.GET)
    public String deleteCrawler(@PathVariable String crawlerID) {


        return crawlerService.isExist(crawlerID)?eventService.deleteEvent(crawlerID):"Crawler not exit";
    }

}
