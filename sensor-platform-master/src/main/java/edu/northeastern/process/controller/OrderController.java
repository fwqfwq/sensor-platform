package edu.northeastern.process.controller;

import edu.northeastern.process.reqres.EventRequest;
import edu.northeastern.process.serivce.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by F Wu
 */

@RestController
public class OrderController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderService service;

    @Value("${csv.data.path}")
    private String path;

    @Value("${csv.input1.path}")
    private String input1;

    @Value("${csv.input2.path}")
    private String input2;

    @Value("${order.table.name}")
    private String tableName;

    @RequestMapping(value = "/order")
    public ResponseEntity<String> index() throws IOException, InterruptedException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        service.start(path, input1, input2);

        String sql = "select * from " + tableName + " order by amount desc";
        List<Map<String, Object>> orderlist =  jdbcTemplate.queryForList(sql);

        return ResponseEntity.ok().body(">>>> Order Services - Automated Order Dispatcher" + orderlist.toString());
    }

}
