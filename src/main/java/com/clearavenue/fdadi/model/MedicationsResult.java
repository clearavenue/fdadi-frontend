package com.clearavenue.fdadi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MedicationsResult {

	@Builder.Default
	private List<Medication> medications = new ArrayList<>();
}
