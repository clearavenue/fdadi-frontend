/*
 *
 */
package com.clearavenue.fdadi.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserProfile.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfile {

	@EqualsAndHashCode.Exclude
	private Long id;

	/** The user id. */
	private String userId;

	/** The password. */
	private String password;

	/** The medications. */
	@Builder.Default
	@EqualsAndHashCode.Exclude
	private List<Medication> medications = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("%s (%d medications)", userId, medications.size());
	}
}
