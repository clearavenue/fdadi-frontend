/*
 *
 */
package com.clearavenue.fdadi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class AllMedications.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Medication implements Comparable<Medication> {

	@EqualsAndHashCode.Exclude
	private Long id;

	private String medicationName;

	public static class MedicationBuilder {
		String medicationName;

		public MedicationBuilder medicationName(final String text) {
			this.medicationName = text.toUpperCase();
			return this;
		}
	}

	@Override
	public int compareTo(final Medication o) {
		return medicationName.compareTo(o.getMedicationName());
	}
}
