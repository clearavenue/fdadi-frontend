package com.clearavenue.fdadi;

import javax.net.ssl.SSLException;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
@RequiredArgsConstructor
@Slf4j
public class FdadiServiceApplication {

	private final BuildProperties buildProperties;

	@Bean
	public WebClient.Builder clientBuilder() {
		return WebClient.builder();
	}

	@Bean
	public WebClient getWebClient() throws SSLException {
		SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(context));
		return clientBuilder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	@Bean
	public ApplicationRunner osLogger(final Environment environment) {
		return (arguments) -> {
			log.info("Starting {} : {}", buildProperties.getName(), buildProperties.getVersion());
			log.info("Running on {} {} ({})", environment.getProperty("os.name"), environment.getProperty("os.version"),
					environment.getProperty("os.arch"));
			log.info("user service @ {}", environment.getProperty("user.service.url"));
			log.info("medication service @ {}", environment.getProperty("medication.service.url"));
		};
	}

	public static void main(final String[] args) {
		SpringApplication.run(FdadiServiceApplication.class, args);
	}

}
