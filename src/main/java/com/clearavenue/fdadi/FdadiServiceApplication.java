package com.clearavenue.fdadi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
public class FdadiServiceApplication {

	@Bean
	@LoadBalanced
	public WebClient.Builder getwebClientBuilder() {
		return WebClient.builder();
	}

	public static void main(final String[] args) {
		SpringApplication.run(FdadiServiceApplication.class, args);
	}

}
