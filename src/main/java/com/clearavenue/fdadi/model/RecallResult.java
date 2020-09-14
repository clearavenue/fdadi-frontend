package com.clearavenue.fdadi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "meta", "results" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecallResult {

	@JsonProperty("meta")
	public Meta meta;

	@JsonProperty("results")
	@Builder.Default
	public List<RecallResults> results = null;
}
