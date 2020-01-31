package com.clearavenue.fdadi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.model.UserResult;

@Service
public class UserProfileService {

	@Autowired
	WebClient.Builder webclientBuilder;

	@Value("${user.service.url}")
	private String userServiceUrl;

	public Optional<UserProfile> findByUserId(final String username) {
		final String uri = String.format("%s/user/%s", userServiceUrl, username);
		final UserResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(UserResult.class).block();
		return result == null ? Optional.empty() : result.getUser();
	}

	public UserProfile save(final UserProfile user) {
		final String uri = String.format("%s/saveUser", userServiceUrl);
		return webclientBuilder.build().post().uri(uri).bodyValue(user).retrieve().bodyToMono(UserProfile.class).block();
	}

	public void deleteAll() {
		final String uri = String.format("%s/deleteAll", userServiceUrl);
		webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(String.class).block();
	}

}
