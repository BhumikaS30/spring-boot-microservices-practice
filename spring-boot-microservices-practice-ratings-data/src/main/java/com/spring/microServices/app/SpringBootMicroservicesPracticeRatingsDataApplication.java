package com.spring.microServices.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.spring.microServices")
@EnableEurekaClient
@SpringBootApplication
public class SpringBootMicroservicesPracticeRatingsDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroservicesPracticeRatingsDataApplication.class, args);
	}

}
