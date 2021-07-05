package edu.northeastern.process.controller;


import edu.northeastern.process.serivce.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CrawlerController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CrawlerService service;

    @RequestMapping(value = "/crawler")
    public ResponseEntity<String> index() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String ans = "Web Crawler services..\n\n>>>> Process start....\n\n";
        service.demoProcess();

        return ResponseEntity.ok().body(ans);
    }

}
