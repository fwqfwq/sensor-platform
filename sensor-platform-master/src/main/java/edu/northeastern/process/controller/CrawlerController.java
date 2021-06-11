package edu.northeastern.process.controller;

import edu.northeastern.process.serivce.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

    @Autowired
    CrawlerService service;

    @RequestMapping(value = "/")
    public ResponseEntity<String> index() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        String ans = "{\"\n\nWeb Crawler services..\"}";
        return ResponseEntity.ok().body(ans);
    }

//    @RequestMapping(value = "/start", method = RequestMethod.GET)
//    public String crawlerProcess() {
//        service.startProcess();
//        return "start crawler";
//    }
}
