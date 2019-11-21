/*
 *
 */
package com.clearavenue.fdadi.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class AllMedications.
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Medication implements Comparable<Medication> {

	@Id
	@EqualsAndHashCode.Exclude
	private ObjectId id;

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
