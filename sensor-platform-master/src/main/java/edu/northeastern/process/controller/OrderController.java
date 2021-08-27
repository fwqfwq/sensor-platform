package edu.northeastern.process.controller;


//import edu.northeastern.process.serivce.OrderService;
import edu.northeastern.process.serivce.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by F Wu
 */

@RestController
public class OrderController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderService service;

    @RequestMapping(value = "/order")
    public ResponseEntity<String> index() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

//        if(service.isExist())

        return ResponseEntity.ok().body(">>>> Order Services");
//        else
    }

}
