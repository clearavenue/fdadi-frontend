package com.clearavenue.fdadi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "meta", "results" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelResult {

	@JsonProperty("meta")
	public Meta meta;

	@Builder.Default
	@JsonProperty("results")
	public List<LabelResults> results = null;
}