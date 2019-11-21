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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;
import reactor.retry.RetryExhaustedException;

/**
 * The Class FDADIController.
 */
@Controller
@Slf4j
public class FDADIController {

	@Autowired
	UserProfileService userService;

	@Autowired
	MedicationService medService;

	@Autowired
	PharmClassService pharmService;

	/**
	 * Index.
	 *
	 * @param req   the req
	 * @param model the map
	 * @return the string
	 */
	@GetMapping("/")
	public final String index(final HttpSession session, final ModelMap model) {
		return "index";
	}

	// simulate login
	@GetMapping("/login")
	public final String login(final HttpSession session, final ModelMap model) {
		try {
			registerUser("DemoUser", "DemoPassword");
		} catch (final RetryExhaustedException e) {
			log.warn("Could not connect to FDADI-USER-SERVICE within retry period");
			model.addAttribute("errorMessage", "FDADI-USER-SERVICE not available, try again in a few minutes");
			return "index";
		}
		session.setAttribute("username", "DemoUser");
		return "redirect:/homepage";
	}

	/**
	 * Register.
	 *
	 * @param username the username
	 * @param pwd      the pwd
	 * @return true, if successful
	 */
	private boolean registerUser(final String username, final String pwd) {
		boolean result = false;

		final Optional<UserProfile> existingUser = userService.findByUserId(username);
		if (existingUser.isEmpty()) {
			log.debug("registerUser - user not found so saving");
			userService.save(UserProfile.builder().userId(username).password(pwd).build());
			result = true;
		}

		return result;
	}

	@GetMapping("/logout")
	public final String logout(final HttpSession session, final ModelMap model) {
		session.removeAttribute("username");
		return "redirect:/";
	}

	@GetMapping("/homepage")
	public final String homepage(final HttpSession session, final ModelMap model) {
		// check if logged in and if not redirect to login
		final String loggedInUsername = (String) session.getAttribute("username");
		if (StringUtils.isBlank(loggedInUsername)) {
			model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
			return "index";
		}

		Optional<UserProfile> loggedInUser = Optional.empty();
		try {
			loggedInUser = userService.findByUserId((String) session.getAttribute("username"));
		} catch (final RetryExhaustedException e) {
			log.warn("Could not connect to FDADI-USER-SERVICE within retry period");
			model.addAttribute("errorMessage", "FDADI-USER-SERVICE not available, try again in a few minutes");
			return "redirect:/logout";
		}

		final UserProfile user = loggedInUser.get();
		log.debug("/ - {} is logged in", user.getUserId());

		final List<Medication> userMeds = user.getMedications();
		Collections.sort(userMeds);
		model.addAttribute("medList", userMeds);
		log.debug("/ - medList:{}", userMeds.size());

		final List<String> userMedList = new ArrayList<>();
		userMeds.stream().map(med -> med.getMedicationName()).forEach(userMedList::add);
		log.debug("/ - userMedList:{}", userMedList.size());

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

		try {
			final List<Medication> all = medService.findAll();
			model.addAttribute("allMeds", all);
		} catch (final RetryExhaustedException e) {
			log.warn("Could not connect to FDADI-MEDICATION-SERVICE within retry period");
			model.addAttribute("errorMessage", "FDADI-MEDICATION-SERVICE not available, try again in a few minutes");
			return "homepage";
		}

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

		return "redirect:/homepage";
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

		return "redirect:/homepage";
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
			return "redirect:/login";
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
