package edu.northeastern.process.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DBController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${crawler.table.name}")
    private String tableName;

    @RequestMapping("/result")
    public List<Map<String, Object>> getDbType() {
        String sql = "select * from " + tableName;
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);

        return list;
    }




}
