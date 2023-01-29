package com.spring.microservices.controller;

import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

public class TestCallable implements Callable<String> {

    @Override
    public String call() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8081/catalog/bulk", String.class);
    }
}
