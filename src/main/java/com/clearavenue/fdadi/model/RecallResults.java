package com.clearavenue.fdadi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "country", "city", "reason_for_recall", "address_1", "address_2", "code_info", "product_quantity", "center_classification_date", "distribution_pattern",
		"state", "product_description", "report_date", "classification", "openfda", "recall_number", "recalling_firm", "initial_firm_notification", "event_id", "product_type",
		"termination_date", "recall_initiation_date", "postal_code", "voluntary_mandated", "status", "more_code_info" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecallResults {
	@JsonProperty("country")
	public String country;
	@JsonProperty("city")
	public String city;
	@JsonProperty("reason_for_recall")
	public String reasonForRecall;
	@JsonProperty("address_1")
	public String address1;
	@JsonProperty("address_2")
	public String address2;
	@JsonProperty("code_info")
	public String codeInfo;
	@JsonProperty("product_quantity")
	public String productQuantity;
	@JsonProperty("center_classification_date")
	public String centerClassificationDate;
	@JsonProperty("distribution_pattern")
	public String distributionPattern;
	@JsonProperty("state")
	public String state;
	@JsonProperty("product_description")
	public String productDescription;
	@JsonProperty("report_date")
	public String reportDate;
	@JsonProperty("classification")
	public String classification;
	@JsonProperty("openfda")
	public RecallOpenfda openfda;
	@JsonProperty("recall_number")
	public String recallNumber;
	@JsonProperty("recalling_firm")
	public String recallingFirm;
	@JsonProperty("initial_firm_notification")
	public String initialFirmNotification;
	@JsonProperty("event_id")
	public String eventId;
	@JsonProperty("product_type")
	public String productType;
	@JsonProperty("termination_date")
	public String terminationDate;
	@JsonProperty("recall_initiation_date")
	public String recallInitiationDate;
	@JsonProperty("postal_code")
	public String postalCode;
	@JsonProperty("voluntary_mandated")
	public String voluntaryMandated;
	@JsonProperty("status")
	public String status;
	@JsonProperty("more_code_info")
	public Object moreCodeInfo;
}