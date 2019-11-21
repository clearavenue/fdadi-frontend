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
 * The Class AllPharmClasses.
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PharmClass {

	@Id
	@EqualsAndHashCode.Exclude
	private ObjectId id;

	private String pharmClassName;

	public static class PharmClassBuilder {
		String pharmClassName;

		public PharmClassBuilder pharmClassName(final String text) {
			this.pharmClassName = text.toUpperCase();
			return this;
		}
	}

}
