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
 * The Class AllPharmClasses.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PharmClass {

	@EqualsAndHashCode.Exclude
	private Long id;

	private String pharmClassName;

	public static class PharmClassBuilder {
		String pharmClassName;

		public PharmClassBuilder pharmClassName(final String text) {
			this.pharmClassName = text.toUpperCase();
			return this;
		}
	}

}
