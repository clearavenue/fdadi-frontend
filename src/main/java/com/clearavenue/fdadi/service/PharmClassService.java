package com.clearavenue.fdadi.service;

import com.clearavenue.fdadi.model.AllPharmClassesResult;
import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.MedicationsResult;
import com.clearavenue.fdadi.model.PharmClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmClassService {

    @Value("${medication.service.url}")
    private String medicationServiceUrl;

    final WebClient.Builder clientBuilder;

    public List<PharmClass> findAll() {
        var uri = String.format("%s/allPharmClasses", medicationServiceUrl);
        var result = clientBuilder.build().get().uri(uri).retrieve().bodyToMono(AllPharmClassesResult.class).block();
        return result == null ? Collections.emptyList() : result.getPharmClasses();
    }

    public List<Medication> findByPharmClass(final String pharmClass) {
        var uri = String.format("%s/medicationsByPharmClass/%s", medicationServiceUrl, pharmClass);
        var result = clientBuilder.build().get().uri(uri).retrieve().bodyToMono(MedicationsResult.class).block();
        return result == null ? Collections.emptyList() : result.getMedications();
    }

}
