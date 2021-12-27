package com.clearavenue.fdadi.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.AllMedicationsResult;
import com.clearavenue.fdadi.model.GetMedicationResult;
import com.clearavenue.fdadi.model.LabelResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationDetailsResult;
import com.clearavenue.fdadi.model.UserProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicationService {

	@Value("${medication.service.url}")
	private String medicationServiceUrl;

	private final WebClient webClient;

	public List<Medication> findAll() {
		var uri = String.format("%s/allMedications", medicationServiceUrl);
		var result = webClient.get().uri(uri).retrieve().bodyToMono(AllMedicationsResult.class).block();
		return result == null ? Collections.emptyList() : result.getMedications();
	}

	public void addUserMedication(final UserProfile user, final String medicationName) {
		var uri = String.format("%s/medication/%s", medicationServiceUrl, medicationName);
		final GetMedicationResult result = webClient.get().uri(uri).retrieve().bodyToMono(GetMedicationResult.class)
				.block();

		final Optional<Medication> medication = result == null ? Optional.empty() : result.getMedication();
		if (medication.isPresent() && !user.getMedications().contains(medication.get())) {
			user.getMedications().add(medication.get());
		}
	}

	public void removeUserMedication(final UserProfile user, final String medicationName) {
		var uri = String.format("%s/medication/%s", medicationServiceUrl, medicationName);
		var result = webClient.get().uri(uri).retrieve().bodyToMono(GetMedicationResult.class).block();

		final Optional<Medication> medication = result == null ? Optional.empty() : result.getMedication();
		if (medication.isPresent() && user.getMedications().contains(medication.get())) {
			user.getMedications().remove(medication.get());
		}
	}

	public LabelResult getDetails(final String medicationName) {
		var uri = String.format("%s/medicationDetails/%s", medicationServiceUrl, medicationName);
		var result = webClient.get().uri(uri).retrieve().bodyToMono(MedicationDetailsResult.class).block();
		return result == null ? LabelResult.builder().build() : result.getLabelResult();
	}

}
