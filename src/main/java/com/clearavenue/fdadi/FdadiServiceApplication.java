package com.clearavenue.fdadi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.service.UserProfileService;

@EnableEurekaClient
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class FdadiServiceApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder getwebClientBuilder() {
		return WebClient.builder();
	}

	@Autowired
	UserProfileService userService;

	public static void main(final String[] args) {
		SpringApplication.run(FdadiServiceApplication.class, args);
	}

}
