/*
 *
 */
package com.clearavenue.fdadi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
     * @param model   the map
     * @return the string
     */
    @GetMapping("/")
    public final String index(final HttpSession session, final ModelMap model) {
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

        final var user = userService.findByUserId((String) session.getAttribute("username")).get();
        final var userMeds = user.getMedications();
        Collections.sort(userMeds);
        model.addAttribute("medList", userMeds);

        final var userMedList = new ArrayList<String>();
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

        log.info("logging controller enter addMedByName : {}", medService.findAll().size());
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

        log.info("logging controller enter processAddMeds : {}", selected.getSelected().length);

        if (selected.getSelected().length != 0) {
            final var user = userService.findByUserId((String) session.getAttribute("username")).get();
            log.info("logging controller enter processAddMeds : {} : {}", user, user.getMedications());
            List.of(selected.getSelected()).stream().forEach(med -> {
                log.info("adding to user : {}", med);
                medService.addUserMedication(user, med);
            });
            log.info("logging controller enter processAddMeds : {} : {}", user, user.getMedications());
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

        final var user = userService.findByUserId((String) session.getAttribute("username")).get();

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

        log.info("logging controller enter addMedByPharmClass : {}", pharmService.findAll().size());
        return "addMedByPharmClass";
    }

    @PostMapping("/processAddMedByPharmClass")
    public final String processAddMedByPharmClass(@ModelAttribute("selected") final SelectedValues selected, final HttpSession session, final ModelMap model) {
        // check if logged in and if not redirect to login
        if (StringUtils.isBlank((String) session.getAttribute("username"))) {
            model.addAttribute("errorMessage", "Please click the [Demo Login] button first");
            return "index";
        }

        final var medications = new ArrayList<Medication>();
        final var pharmClassList = List.of(selected.getSelected());

        pharmClassList.forEach(pharmClass -> pharmService.findByPharmClass(pharmClass).forEach(medications::add));

        model.addAttribute("selected", new SelectedValues());
        model.addAttribute("allMeds", medications);

        log.info("logging controller enter processAddMedByPharmClass : {}", medications.size());

        return "addMedByName";
    }

}
