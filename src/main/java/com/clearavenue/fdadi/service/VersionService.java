package com.clearavenue.fdadi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {

	private final BuildProperties buildProperties;

	public String version() {
		return String.format("%s : %s", buildProperties.getArtifact(), buildProperties.getVersion());
	}

}
