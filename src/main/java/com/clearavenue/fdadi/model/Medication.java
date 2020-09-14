/*
 *
 */
package com.clearavenue.fdadi.model;

import lombok.*;

import java.util.Locale;

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
			this.medicationName = text.toUpperCase(Locale.getDefault());
			return this;
		}
	}

	@Override
	public int compareTo(final Medication o) {
		return medicationName.compareTo(o.getMedicationName());
	}
}
