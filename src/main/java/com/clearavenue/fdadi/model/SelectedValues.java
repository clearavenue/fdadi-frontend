/*
 *
 */
package com.clearavenue.fdadi.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

@Data
@SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Needed by thymeleaf")
public class SelectedValues {

	private String[] selected;

}
