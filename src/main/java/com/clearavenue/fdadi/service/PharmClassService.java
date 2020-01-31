<<<<<<< HEAD
package com.clearavenue.fdadi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.AllPharmClassesResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationsResult;
import com.clearavenue.fdadi.model.PharmClass;

@Service
public class PharmClassService {

	@Autowired
	WebClient.Builder webclientBuilder;

	@Value("${medication.service.url}")
	private String medicationServiceUrl;

	public List<PharmClass> findAll() {
		final String uri = String.format("%s/allPharmClasses", medicationServiceUrl);
		final AllPharmClassesResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(AllPharmClassesResult.class).block();
		return result == null ? Collections.emptyList() : result.getPharmClasses();
	}

	public List<Medication> findByPharmClass(final String pharmClass) {
		final String uri = String.format("%s/medicationsByPharmClass/%s", medicationServiceUrl, pharmClass);
		final MedicationsResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(MedicationsResult.class).block();
		return result == null ? Collections.emptyList() : result.getMedications();
	}

}
=======
package com.clearavenue.fdadi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clearavenue.fdadi.model.AllPharmClassesResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationsResult;
import com.clearavenue.fdadi.model.PharmClass;

@Service
public class PharmClassService {

	@Autowired
	WebClient.Builder webclientBuilder;

	public List<PharmClass> findAll() {
		final String uri = "http://fdadi-medication-service:8083/allPharmClasses";
		final AllPharmClassesResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(AllPharmClassesResult.class).block();
		return result == null ? Collections.emptyList() : result.getPharmClasses();
	}

	public List<Medication> findByPharmClass(final String pharmClass) {
		final String uri = String.format("http://fdadi-medication-service:8083/medicationsByPharmClass/%s", pharmClass);
		final MedicationsResult result = webclientBuilder.build().get().uri(uri).retrieve().bodyToMono(MedicationsResult.class).block();
		return result == null ? Collections.emptyList() : result.getMedications();
	}

}
>>>>>>> dff66ccfd87b7d354b5b9ebf53b25bbc4a737b62
