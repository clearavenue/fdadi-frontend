package com.clearavenue.fdadi.controller;

import com.clearavenue.fdadi.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VersionController {

	private final VersionService versionService;

	@GetMapping("/version")
	public String getVersion() {
		return versionService.version();
	}
}
