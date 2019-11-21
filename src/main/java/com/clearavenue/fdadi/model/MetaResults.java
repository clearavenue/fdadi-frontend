package com.clearavenue.fdadi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "skip", "limit", "total" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaResults {
	@JsonProperty("skip")
	public Integer skip;
	@JsonProperty("limit")
	public Integer limit;
	@JsonProperty("total")
	public Integer total;
}