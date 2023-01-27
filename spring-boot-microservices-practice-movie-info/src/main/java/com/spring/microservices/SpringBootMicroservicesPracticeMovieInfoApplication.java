package com.spring.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
public class SpringBootMicroservicesPracticeMovieInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMicroservicesPracticeMovieInfoApplication.class, args);
    }
}
