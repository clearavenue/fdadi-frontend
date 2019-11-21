package com.clearavenue.fdadi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "effective_time", "inactive_ingredient", "purpose", "keep_out_of_reach_of_children", "warnings", "questions", "spl_product_data_elements", "ask_doctor",
		"other_safety_information", "openfda", "version", "dosage_and_administration", "pregnancy_or_breast_feeding", "stop_use", "do_not_use",
		"package_label_principal_display_panel", "indications_and_usage", "set_id", "id", "ask_doctor_or_pharmacist", "active_ingredient", "spl_unclassified_section_table",
		"drug_interactions", "recent_major_changes", "geriatric_use", "pharmacodynamics", "spl_patient_package_insert", "description", "nonclinical_toxicology",
		"dosage_forms_and_strengths", "clinical_pharmacology_table", "storage_and_handling", "mechanism_of_action", "pharmacokinetics", "clinical_studies_table",
		"teratogenic_effects", "pediatric_use", "pharmacodynamics_table", "contraindications", "pregnancy", "nursing_mothers", "adverse_reactions_table", "warnings_and_cautions",
		"recent_major_changes_table", "adverse_reactions", "animal_pharmacology_and_or_toxicology", "spl_unclassified_section", "use_in_specific_populations", "how_supplied_table",
		"how_supplied", "information_for_patients", "clinical_studies", "clinical_pharmacology", "carcinogenesis_and_mutagenesis_and_impairment_of_fertility", "overdosage" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabelResults {
	@JsonProperty("effective_time")
	public String effectiveTime;

	@JsonProperty("inactive_ingredient")
	@Builder.Default
	public List<String> inactiveIngredient = new ArrayList<>();

	@JsonProperty("purpose")
	@Builder.Default
	public List<String> purpose = new ArrayList<>();

	@JsonProperty("keep_out_of_reach_of_children")
	@Builder.Default
	public List<String> keepOutOfReachOfChildren = new ArrayList<>();

	@JsonProperty("warnings")
	@Builder.Default
	public List<String> warnings = new ArrayList<>();

	@JsonProperty("questions")
	@Builder.Default
	public List<String> questions = new ArrayList<>();

	@JsonProperty("spl_product_data_elements")
	@Builder.Default
	public List<String> splProductDataElements = new ArrayList<>();

	@JsonProperty("ask_doctor")
	@Builder.Default
	public List<String> askDoctor = new ArrayList<>();

	@JsonProperty("other_safety_information")
	@Builder.Default
	public List<String> otherSafetyInformation = new ArrayList<>();

	@JsonProperty("openfda")
	public LabelOpenfda openfda;

	@JsonProperty("version")
	public String version;

	@JsonProperty("dosage_and_administration")
	@Builder.Default
	public List<String> dosageAndAdministration = new ArrayList<>();

	@JsonProperty("pregnancy_or_breast_feeding")
	@Builder.Default
	public List<String> pregnancyOrBreastFeeding = new ArrayList<>();

	@JsonProperty("stop_use")
	@Builder.Default
	public List<String> stopUse = new ArrayList<>();

	@JsonProperty("do_not_use")
	@Builder.Default
	public List<String> doNotUse = new ArrayList<>();

	@JsonProperty("package_label_principal_display_panel")
	@Builder.Default
	public List<String> packageLabelPrincipalDisplayPanel = new ArrayList<>();

	@JsonProperty("indications_and_usage")
	@Builder.Default
	public List<String> indicationsAndUsage = new ArrayList<>();

	@JsonProperty("set_id")
	public String setId;

	@JsonProperty("id")
	public String id;

	@JsonProperty("ask_doctor_or_pharmacist")
	@Builder.Default
	public List<String> askDoctorOrPharmacist = new ArrayList<>();

	@JsonProperty("active_ingredient")
	@Builder.Default
	public List<String> activeIngredient = new ArrayList<>();

	@JsonProperty("spl_unclassified_section_table")
	@Builder.Default
	public List<String> splUnclassifiedSectionTable = new ArrayList<>();

	@JsonProperty("drug_interactions")
	@Builder.Default
	public List<String> drugInteractions = new ArrayList<>();

	@JsonProperty("recent_major_changes")
	@Builder.Default
	public List<String> recentMajorChanges = new ArrayList<>();

	@JsonProperty("geriatric_use")
	@Builder.Default
	public List<String> geriatricUse = new ArrayList<>();

	@JsonProperty("pharmacodynamics")
	@Builder.Default
	public List<String> pharmacodynamics = new ArrayList<>();

	@JsonProperty("spl_patient_package_insert")
	@Builder.Default
	public List<String> splPatientPackageInsert = new ArrayList<>();

	@JsonProperty("description")
	@Builder.Default
	public List<String> description = new ArrayList<>();

	@JsonProperty("nonclinical_toxicology")
	@Builder.Default
	public List<String> nonclinicalToxicology = new ArrayList<>();

	@JsonProperty("dosage_forms_and_strengths")
	@Builder.Default
	public List<String> dosageFormsAndStrengths = new ArrayList<>();

	@JsonProperty("clinical_pharmacology_table")
	@Builder.Default
	public List<String> clinicalPharmacologyTable = new ArrayList<>();

	@JsonProperty("storage_and_handling")
	@Builder.Default
	public List<String> storageAndHandling = new ArrayList<>();

	@JsonProperty("mechanism_of_action")
	@Builder.Default
	public List<String> mechanismOfAction = new ArrayList<>();

	@JsonProperty("pharmacokinetics")
	@Builder.Default
	public List<String> pharmacokinetics = new ArrayList<>();

	@JsonProperty("clinical_studies_table")
	@Builder.Default
	public List<String> clinicalStudiesTable = new ArrayList<>();

	@JsonProperty("teratogenic_effects")
	@Builder.Default
	public List<String> teratogenicEffects = new ArrayList<>();

	@JsonProperty("pediatric_use")
	@Builder.Default
	public List<String> pediatricUse = new ArrayList<>();

	@JsonProperty("pharmacodynamics_table")
	@Builder.Default
	public List<String> pharmacodynamicsTable = new ArrayList<>();

	@JsonProperty("contraindications")
	@Builder.Default
	public List<String> contraindications = new ArrayList<>();

	@JsonProperty("pregnancy")
	@Builder.Default
	public List<String> pregnancy = new ArrayList<>();

	@JsonProperty("nursing_mothers")
	@Builder.Default
	public List<String> nursingMothers = new ArrayList<>();

	@JsonProperty("adverse_reactions_table")
	@Builder.Default
	public List<String> adverseReactionsTable = new ArrayList<>();

	@JsonProperty("warnings_and_cautions")
	@Builder.Default
	public List<String> warningsAndCautions = new ArrayList<>();

	@JsonProperty("recent_major_changes_table")
	@Builder.Default
	public List<String> recentMajorChangesTable = new ArrayList<>();

	@JsonProperty("adverse_reactions")
	@Builder.Default
	public List<String> adverseReactions = new ArrayList<>();

	@JsonProperty("animal_pharmacology_and_or_toxicology")
	@Builder.Default
	public List<String> animalPharmacologyAndOrToxicology = new ArrayList<>();

	@JsonProperty("spl_unclassified_section")
	@Builder.Default
	public List<String> splUnclassifiedSection = new ArrayList<>();

	@JsonProperty("use_in_specific_populations")
	@Builder.Default
	public List<String> useInSpecificPopulations = new ArrayList<>();

	@JsonProperty("how_supplied_table")
	@Builder.Default
	public List<String> howSuppliedTable = new ArrayList<>();

	@JsonProperty("how_supplied")
	@Builder.Default
	public List<String> howSupplied = new ArrayList<>();

	@JsonProperty("information_for_patients")
	@Builder.Default
	public List<String> informationForPatients = new ArrayList<>();

	@JsonProperty("clinical_studies")
	@Builder.Default
	public List<String> clinicalStudies = new ArrayList<>();

	@JsonProperty("clinical_pharmacology")
	@Builder.Default
	public List<String> clinicalPharmacology = new ArrayList<>();

	@JsonProperty("carcinogenesis_and_mutagenesis_and_impairment_of_fertility")
	@Builder.Default
	public List<String> carcinogenesisAndMutagenesisAndImpairmentOfFertility = new ArrayList<>();

	@JsonProperty("overdosage")
	@Builder.Default
	public List<String> overdosage = new ArrayList<>();
}
