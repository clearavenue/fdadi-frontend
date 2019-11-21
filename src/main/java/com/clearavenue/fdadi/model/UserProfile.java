/*
 *
 */
package com.clearavenue.fdadi.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class UserProfile.
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfile {

	@Id
	@EqualsAndHashCode.Exclude
	private ObjectId id;

	/** The user id. */
	private String userId;

	/** The password. */
	private String password;

	/** The medications. */
	@Builder.Default
	private List<Medication> medications = new ArrayList<>();

	@Override
	public String toString() {
		return String.format("%s (%d medications)", userId, medications.size());
	}
}
