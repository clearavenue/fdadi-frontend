package com.clearavenue.fdadi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class FdadiServiceApplication {

    private final BuildProperties buildProperties;

    @Bean
    public WebClient.Builder clientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ApplicationRunner osLogger(Environment environment) {
        return (arguments) -> {
            log.info("Starting {} : {}", buildProperties.getName(), buildProperties.getVersion());
            log.info("Running on {} {} ({})", environment.getProperty("os.name"), environment.getProperty("os.version"), environment.getProperty("os.arch"));
        };
    }

    public static void main(final String[] args) {
        SpringApplication.run(FdadiServiceApplication.class, args);
    }

}
