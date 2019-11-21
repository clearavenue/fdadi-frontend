package com.clearavenue.fdadi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AllMedicationsResult {

	@Builder.Default
	private List<Medication> medications = new ArrayList<>();
}
