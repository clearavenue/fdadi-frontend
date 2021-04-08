/*
 *
 */
package com.clearavenue.fdadi.controller;

import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.SelectedValues;
import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.service.MedicationService;
import com.clearavenue.fdadi.service.PharmClassService;
import com.clearavenue.fdadi.service.UserProfileService;
import com.clearavenue.fdadi.service.VersionService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class FDADIController.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class FDADIController {

	private final UserProfileService userService;

	private final MedicationService medService;

	private final PharmClassService pharmService;

	private final VersionService versionService;

	/**
	 * Index.
	 *
	 *
	 * @param session
	 * @param model the map
	 * @return the string
	 */
	@GetMapping("/")
	public final String index(HttpSession session, final ModelMap model) {
		model.addAttribute("version", versionService.version());
		return "index";
	}

	// simulate login
	@GetMapping("/login")
	public final String login(final HttpSession session) {
		userService.deleteAll();
		userService.save(UserProfile.builder().userId("DemoUser").password("DemoPassword1").build());
		session.setAttribute("username", "DemoUser");
		return "redirect:homepage";
	}

	@GetMapping("/logout")
	public final String logout(final HttpSession session) {
		session.removeAttribute("username");
		return "redirect:/";
	}

	@GetMapping("/homepage")
	public final String homepage(final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}


		var user = userService.findByUserId((String) session.getAttribute("username")).get();
		var userMeds = user.getMedications();
		Collections.sort(userMeds);
		model.addAttribute("medList", userMeds);

		var userMedList = new ArrayList<String>();
		userMeds.stream().map(med -> med.getMedicationName()).forEach(userMedList::add);
		return "homepage";
	}

	/**
	 * Faq.
	 *
	 * @return the string
	 */
	@GetMapping("/faq")
	public final String faq() {
		return "faq";
	}

	/**
	 * Adds the med by name.
	 *
	 * @return the string
	 */
	@GetMapping("/addMedByName")
	public final String addMedByName(final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		model.addAttribute("selected", new SelectedValues());
		model.addAttribute("allMeds", medService.findAll());

		return "addMedByName";
	}

	/**
	 * Process add med by name.
	 *
	 * @return the string
	 */
	@PostMapping("/processAddMeds")
	public final String processAddMedByName(@ModelAttribute("selected") final SelectedValues selected, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		if (selected.getSelected().length != 0) {
			var user = userService.findByUserId((String) session.getAttribute("username")).get();
			List.of(selected.getSelected()).stream().forEach(med -> medService.addUserMedication(user,med));
			userService.save(user);
		}

		return "redirect:homepage";
	}

	@GetMapping("/removeMedication/{medicationName}")
	public final String removeMedication(final @PathVariable String medicationName, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return index(session, model);
		}

		var user = userService.findByUserId((String) session.getAttribute("username")).get();

		medService.removeUserMedication(user, medicationName);
		userService.save(user);

		return "redirect:homepage";
	}

	@GetMapping("/medicationDetails/{medicationName}")
	public final String medicationDetails(final @PathVariable String medicationName, final ModelMap model) {
		model.addAttribute("medicationDetails", medService.getDetails(medicationName));
		return "medicationDetails";
	}

	@GetMapping("/addMedByPharmClass")
	public final String addMedByPharmClass(final HttpServletRequest req, final ModelMap model) {
		// check if logged in and if not redirect to login
		final HttpSession session = req.getSession();
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			return "redirect:login";
		}

		model.addAttribute("allPharmClasses", pharmService.findAll());
		model.addAttribute("selected", new SelectedValues());

		return "addMedByPharmClass";
	}

	@PostMapping("/processAddMedByPharmClass")
	public final String processAddMedByPharmClass(@ModelAttribute("selected") final SelectedValues selected, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		if (StringUtils.isBlank((String) session.getAttribute("username"))) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		var medications = new ArrayList<Medication>();
		var pharmClassList = List.of(selected.getSelected());

		pharmClassList.forEach(pharmClass -> pharmService.findByPharmClass(pharmClass).forEach(medications::add));

		model.addAttribute("meds", new SelectedValues());
		model.addAttribute("allMeds", medications);

		return "addMedByName";
	}

}
