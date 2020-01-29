package com.clearavenue.fdadi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class FdadiServiceApplication {

	@Bean
	public WebClient.Builder getwebClientBuilder() {
		return WebClient.builder();
	}

	public static void main(final String[] args) {
		SpringApplication.run(FdadiServiceApplication.class, args);
	}

}
