package com.clearavenue.fdadi;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class FdadiServiceApplication {

	@Bean
	public WebClient.Builder getwebClientBuilder() {
		return WebClient.builder();
	}

	@Autowired
	private BuildProperties buildProperties;

	public static void main(final String[] args) {
		SpringApplication.run(FdadiServiceApplication.class, args);
	}

	@PostConstruct
	void displayVersion() {
		log.debug("Starting {} : {}", buildProperties.getName(), buildProperties.getVersion());
	}
}
