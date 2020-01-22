package com.clearavenue.fdadi.service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.AllPharmClassesResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationsResult;
import com.clearavenue.fdadi.model.PharmClass;

import lombok.extern.slf4j.Slf4j;
import reactor.retry.Retry;

@Service
@Slf4j
public class PharmClassService {

	@Autowired
	WebClient.Builder webclientBuilder;

	public List<PharmClass> findAll() {
		final String uri = "http://FDADI-MEDICATION-SERVICE/allPharmClasses";
		final AllPharmClassesResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(AllPharmClassesResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();
		return result == null ? Collections.emptyList() : result.getPharmClasses();
	}

	public List<Medication> findByPharmClass(final String pharmClass) {
		final String uri = String.format("http://FDADI-MEDICATION-SERVICE/medicationsByPharmClass/%s", pharmClass);
		final MedicationsResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(MedicationsResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();
		return result == null ? Collections.emptyList() : result.getMedications();
	}

}
