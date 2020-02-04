/*
 *
 */
package com.clearavenue.fdadi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.clearavenue.fdadi.model.Medication;
import com.clearavenue.fdadi.model.PharmClass;
import com.clearavenue.fdadi.model.SelectedValues;
import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.service.MedicationService;
import com.clearavenue.fdadi.service.PharmClassService;
import com.clearavenue.fdadi.service.UserProfileService;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

	/**
	 * Index.
	 *
	 * @param req   the req
	 * @param model the map
	 * @return the string
	 */
	@GetMapping("/app")
	public final String index(final HttpSession session, final ModelMap model) {
		log.debug("/app - showing index");
		return "index";
	}

	// simulate login
	@GetMapping("/login")
	public final String login(final HttpSession session, final ModelMap model) {
		log.debug("start /login");
		log.debug("calling delete all");
		// userService.deleteAll();
		log.debug("saving user");
		userService.save(UserProfile.builder().userId("DemoUser").password("DemoPassword1").build());
		userService.save(UserProfile.builder().userId("DemoUser2").password("DemoPassword2").build());
		log.debug("setting attribute");
		session.setAttribute("username", "DemoUser");
		log.debug("end /login - redirect /homepage");
		return "redirect:homepage";
	}

	@GetMapping("/logout")
	public final String logout(final HttpSession session, final ModelMap model) {
		log.debug("start /logout");
		log.debug("remove username attrib");
		session.removeAttribute("username");
		log.debug("end /logout - redirect /app");
		return "redirect:app";
	}

	@GetMapping("/homepage")
	public final String homepage(final HttpSession session, final ModelMap model) {
		log.debug("start /homepage");

		log.debug("get username from session");
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			log.debug("username not found in session, display index");
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		log.debug("calling user service to findbyuserId");
		final Optional<UserProfile> loggedInUser = userService.findByUserId((String) session.getAttribute("username"));
		final UserProfile user = loggedInUser.get();
		log.debug("/ - {} is logged in", user.getUserId());

		final List<Medication> userMeds = user.getMedications();
		Collections.sort(userMeds);
		model.addAttribute("medList", userMeds);
		log.debug("/ - medList:{}", userMeds.size());

		final List<String> userMedList = new ArrayList<>();
		userMeds.stream().map(med -> med.getMedicationName()).forEach(userMedList::add);
		log.debug("/ - userMedList:{}", userMedList.size());

		log.debug("end /homepage now display homepage");
		return "homepage";
	}

	/**
	 * Faq.
	 *
	 * @param req   the req
	 * @param model the map
	 * @return the string
	 */
	@GetMapping("/faq")
	public final String faq(final HttpServletRequest req, final ModelMap model) {
		return "faq";
	}

	/**
	 * Adds the med by name.
	 *
	 * @param req the req
	 * @param map the map
	 * @return the string
	 */
	@GetMapping("/addMedByName")
	public final String addMedByName(final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		model.addAttribute("selected", new SelectedValues());

		final List<Medication> all = medService.findAll();
		model.addAttribute("allMeds", all);

		return "addMedByName";
	}

	/**
	 * Process add med by name.
	 *
	 * @param req the req
	 * @param map the map
	 * @return the string
	 */
	@PostMapping("/processAddMeds")
	public final String processAddMedByName(@ModelAttribute("selected") final SelectedValues selected, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		if (selected.getSelected().length != 0) {
			final Optional<UserProfile> loggedInUser = userService.findByUserId((String) session.getAttribute("username"));
			final UserProfile user = loggedInUser.get();

			final List<String> medications = Arrays.asList(selected.getSelected());
			medications.forEach(med -> medService.addUserMedication(user, med));
			userService.save(user);
		}

		return "redirect:homepage";
	}

	@GetMapping("/removeMedication/{medicationName}")
	public final String removeMedication(final @PathVariable String medicationName, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return index(session, model);
		}

		final Optional<UserProfile> loggedInUser = userService.findByUserId((String) session.getAttribute("username"));
		final UserProfile user = loggedInUser.get();

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
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			return "redirect:login";
		}

		final List<PharmClass> all = pharmService.findAll();
		model.addAttribute("allPharmClasses", all);
		model.addAttribute("selected", new SelectedValues());

		return "addMedByPharmClass";
	}

	@PostMapping("/processAddMedByPharmClass")
	public final String processAddMedByPharmClass(@ModelAttribute("selected") final SelectedValues selected, final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		final List<Medication> medications = new ArrayList<>();
		final List<String> pharmClassList = Arrays.asList(selected.getSelected());
		pharmClassList.forEach(pharmClass -> pharmService.findByPharmClass(pharmClass).forEach(medications::add));

		model.addAttribute("meds", new SelectedValues());
		model.addAttribute("allMeds", medications);

		return "addMedByName";
	}

}
