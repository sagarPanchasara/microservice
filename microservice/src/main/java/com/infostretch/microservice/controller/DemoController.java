package com.infostretch.microservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${sagar:Config server is not working, please check}")
    private String sagar;

    @GetMapping
    public String getValue() {
        return sagar;
    }
}
