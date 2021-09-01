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

    @Value("${crawler.table.name}")
    private String tableName;

    // Demo url for crawler
    @Value("${crawler.demo1.url}")
    private String url1;

    @Value("${crawler.demo2.url}")
    private String url2;


    @RequestMapping(value = "/crawler", method = RequestMethod.GET)
    public ResponseEntity<String> crawlerProcess() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        return ResponseEntity.ok().body(">>>> Welcome to Crawler Service");
    }


    // Demo url
    @RequestMapping(value = "/crawler/demo", method = RequestMethod.GET)
    public ResponseEntity<String> crawlerDemo() throws IOException, InterruptedException {

        crawlerService.startCrawler(url1, url2);

        // display results, read from database
        String sql = "select * from " + tableName;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);

        return ResponseEntity.ok()
                .body(list.toString());
    }


}
