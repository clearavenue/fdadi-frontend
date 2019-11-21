package com.clearavenue.fdadi.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.AllMedicationsResult;
import com.clearavenue.fdadi.model.GetMedicationResult;
import com.clearavenue.fdadi.model.LabelResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationDetailsResult;
import com.clearavenue.fdadi.model.UserProfile;

import lombok.extern.slf4j.Slf4j;
import reactor.retry.Retry;

@Service
@Slf4j
public class MedicationService {

	@Autowired
	WebClient.Builder webclientBuilder;

	public List<Medication> findAll() {
		final String uri = "http://FDADI-MEDICATION-SERVICE/allMedications";
		final AllMedicationsResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(AllMedicationsResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();
		return result.getMedications();
	}

	public void addUserMedication(final UserProfile user, final String medicationName) {
		final String uri = String.format("http://FDADI-MEDICATION-SERVICE/medication/%s", medicationName);
		final GetMedicationResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(GetMedicationResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();

		final Optional<Medication> medication = result.getMedication();
		if (medication.isPresent() && !user.getMedications().contains(medication.get())) {
			user.getMedications().add(medication.get());
		}
	}

	public void removeUserMedication(final UserProfile user, final String medicationName) {
		final String uri = String.format("http://FDADI-MEDICATION-SERVICE/medication/%s", medicationName);
		final GetMedicationResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(GetMedicationResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();

		final Optional<Medication> medication = result.getMedication();
		if (medication.isPresent() && user.getMedications().contains(medication.get())) {
			user.getMedications().remove(medication.get());
		}
	}

	public LabelResult getDetails(final String medicationName) {
		final String uri = String.format("http://FDADI-MEDICATION-SERVICE/medicationDetails/%s", medicationName);
		final MedicationDetailsResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(MedicationDetailsResult.class).retryWhen(
				Retry.any().exponentialBackoff(Duration.ofSeconds(2), Duration.ofSeconds(30)).retryMax(5).doOnRetry(r -> log.info("Trying FDADI-MEDICATION-SERVICE again...")))
				.block();
		return result.getLabelResult();
	}

}
