package com.clearavenue.fdadi.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetUserResult {

	private Optional<UserProfile> user;
}
