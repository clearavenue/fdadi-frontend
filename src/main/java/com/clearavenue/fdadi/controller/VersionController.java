package com.clearavenue.fdadi.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VersionController {

	private final BuildProperties buildProperties;

	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public String getVersion() {
		return buildProperties.getVersion();
	}
}
