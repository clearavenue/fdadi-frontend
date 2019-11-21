package com.clearavenue.fdadi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "disclaimer", "terms", "license", "last_updated", "results" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
	@JsonProperty("disclaimer")
	public String disclaimer;
	@JsonProperty("terms")
	public String terms;
	@JsonProperty("license")
	public String license;
	@JsonProperty("last_updated")
	public String lastUpdated;
	@JsonProperty("results")
	public MetaResults results;
}