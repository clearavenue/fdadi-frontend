package com.clearavenue.fdadi.service;

import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.model.UserResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

	@Value("${user.service.url}")
	private String userServiceUrl;

	private final WebClient.Builder clientBuilder;

	public Optional<UserProfile> findByUserId(final String username) {
		var uri = String.format("%s/user/%s", userServiceUrl, username);
		var result = clientBuilder.build().get().uri(uri).retrieve().bodyToMono(UserResult.class).block();
		return result == null ? Optional.empty() : result.getUser();
	}

	public UserProfile save(final UserProfile user) {
		var uri = String.format("%s/saveUser", userServiceUrl);
		return clientBuilder.build().post().uri(uri).bodyValue(user).retrieve().bodyToMono(UserProfile.class).block();
	}

	public void deleteAll() {
		var uri = String.format("%s/deleteAll", userServiceUrl);
		clientBuilder.build().get().uri(uri).retrieve().bodyToMono(String.class).block();
	}

}
