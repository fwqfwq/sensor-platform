package edu.northeastern.process.controller;

import edu.northeastern.process.serivce.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.PostLoad;


@RestController
public class DemoController {

    @Autowired
    DemoService service;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demoProcess() {
        service.startProcess();
        return "start the demo process";
    }
}
