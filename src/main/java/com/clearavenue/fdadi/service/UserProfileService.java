package com.clearavenue.fdadi.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.model.UserResult;

import lombok.extern.slf4j.Slf4j;
import reactor.retry.Retry;

@Service
@Slf4j
public class UserProfileService {

	@Autowired
	WebClient.Builder webclientBuilder;

	public Optional<UserProfile> findByUserId(final String username) {
		final String uri = String.format("http://FDADI-USER-SERVICE/user/%s", username);
		final UserResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(UserResult.class)
				.retryWhen(Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-USER-SERVICE again...")))
				.block();
		return result == null ? Optional.empty() : result.getUser();
	}

	public UserProfile save(final UserProfile user) {
		final String uri = "http://FDADI-USER-SERVICE/saveUser";
		return webclientBuilder.build().post().uri(uri).bodyValue(user).retrieve().bodyToMono(UserProfile.class).block();
	}

	public void deleteAll() {
		final String uri = "http://FDADI-USER-SERVICE/deleteAll";
		final String result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(String.class)
				.retryWhen(Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-USER-SERVICE again...")))
				.block();
		log.info("deleteAll = {}", result);
	}

}
