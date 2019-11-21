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
@JsonPropertyOrder({ "product_ndc", "nui", "is_original_packager", "package_ndc", "generic_name", "spl_set_id", "pharm_class_cs", "upc", "brand_name", "manufacturer_name",
		"pharm_class_pe", "rxcui", "unii", "spl_id", "substance_name", "product_type", "route", "pharm_class_moa", "application_number", "pharm_class_epc" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelOpenfda {
	@JsonProperty("product_ndc")
	@Builder.Default
	public List<String> productNdc = null;

	@JsonProperty("nui")
	@Builder.Default
	public List<String> nui = null;

	@JsonProperty("is_original_packager")
	@Builder.Default
	public List<Boolean> isOriginalPackager = null;

	@JsonProperty("package_ndc")
	@Builder.Default
	public List<String> packageNdc = null;

	@JsonProperty("generic_name")
	@Builder.Default
	public List<String> genericName = null;

	@JsonProperty("spl_set_id")
	@Builder.Default
	public List<String> splSetId = null;

	@JsonProperty("pharm_class_cs")
	@Builder.Default
	public List<String> pharmClassCs = null;

	@JsonProperty("upc")
	@Builder.Default
	public List<String> upc = null;

	@JsonProperty("brand_name")
	@Builder.Default
	public List<String> brandName = null;

	@JsonProperty("manufacturer_name")
	@Builder.Default
	public List<String> manufacturerName = null;

	@JsonProperty("pharm_class_pe")
	@Builder.Default
	public List<String> pharmClassPe = null;

	@JsonProperty("rxcui")
	@Builder.Default
	public List<String> rxcui = null;

	@JsonProperty("unii")
	@Builder.Default
	public List<String> unii = null;

	@JsonProperty("spl_id")
	@Builder.Default
	public List<String> splId = null;

	@JsonProperty("substance_name")
	@Builder.Default
	public List<String> substanceName = null;

	@JsonProperty("product_type")
	@Builder.Default
	public List<String> productType = null;

	@JsonProperty("route")
	@Builder.Default
	public List<String> route = null;

	@JsonProperty("pharm_class_moa")
	@Builder.Default
	public List<String> pharmClassMoa = null;

	@JsonProperty("application_number")
	@Builder.Default
	public List<String> applicationNumber = null;

	@JsonProperty("pharm_class_epc")
	@Builder.Default
	public List<String> pharmClassEpc = null;
}