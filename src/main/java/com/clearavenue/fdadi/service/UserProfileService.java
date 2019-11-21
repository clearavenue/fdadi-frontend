package com.clearavenue.fdadi.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.GetUserResult;
import com.clearavenue.fdadi.model.UserProfile;

import lombok.extern.slf4j.Slf4j;
import reactor.retry.Retry;

@Service
@Slf4j
public class UserProfileService {

	@Autowired
	WebClient.Builder webclientBuilder;

	public Optional<UserProfile> findByUserId(final String username) {
		final String uri = String.format("http://FDADI-USER-SERVICE/user/%s", username);
		final GetUserResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(GetUserResult.class)
				.retryWhen(Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-USER-SERVICE again...")))
				.block();
		return result.getUser();
	}

	public UserProfile save(final UserProfile user) {
		final String uri = "http://FDADI-USER-SERVICE/saveUser";
		return webclientBuilder.build().post().uri(uri).bodyValue(user).retrieve().bodyToMono(UserProfile.class).block();
	}

}
