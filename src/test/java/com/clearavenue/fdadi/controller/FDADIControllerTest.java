package com.clearavenue.fdadi.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.clearavenue.fdadi.model.LabelOpenfda;
import com.clearavenue.fdadi.model.LabelResult;
import com.clearavenue.fdadi.model.LabelResults;
import com.clearavenue.fdadi.model.UserProfile;
import com.clearavenue.fdadi.service.MedicationService;
import com.clearavenue.fdadi.service.PharmClassService;
import com.clearavenue.fdadi.service.UserProfileService;

@WebMvcTest(FDADIController.class)
@ActiveProfiles("test")
public class FDADIControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserProfileService userService;

	@MockBean
	private MedicationService medService;

	@MockBean
	private PharmClassService pharmService;

	@MockBean
	BuildProperties buildProperties;

	@MockBean
	GitProperties gitProperties;

	@Test
	public void getHome() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getLogin() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().is3xxRedirection());
		assertTrue(true, "should always be true");
	}

	@Test
	public void getLogout() throws Exception {
		this.mockMvc.perform(get("/logout")).andExpect(status().is3xxRedirection());
		assertTrue(true, "should always be true");
	}

	@Test
	public void getFaq() throws Exception {
		this.mockMvc.perform(get("/faq")).andExpect(status().isOk()).andExpect(view().name("faq"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getHomepageNotLoggedIn() throws Exception {
		this.mockMvc.perform(get("/homepage")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getHomepage() throws Exception {
		given(userService.findByUserId("demouser")).willReturn(Optional.of(UserProfile.builder().userId("demouser").build()));
		this.mockMvc.perform(get("/homepage").sessionAttr("username", "demouser")).andExpect(status().isOk()).andExpect(view().name("homepage"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getAddMedByNameNotLoggedIn() throws Exception {
		this.mockMvc.perform(get("/addMedByName").sessionAttr("username", "demouser")).andExpect(status().isOk()).andExpect(view().name("addMedByName"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getAddMedByName() throws Exception {
		this.mockMvc.perform(get("/addMedByName")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void postAddMedsNotLoggedIn() throws Exception {
		this.mockMvc.perform(post("/processAddMeds")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void postAddMeds() throws Exception {
		given(userService.findByUserId("demouser")).willReturn(Optional.of(UserProfile.builder().userId("demouser").build()));
		this.mockMvc.perform(post("/processAddMeds").sessionAttr("username", "demouser").param("selected", "tylenol")).andExpect(status().is3xxRedirection());
		assertTrue(true, "should always be true");
	}

	@Test
	public void getRemoveMedNotLoggedIn() throws Exception {
		this.mockMvc.perform(get("/removeMedication/tylenol")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getRemoveMed() throws Exception {
		given(userService.findByUserId("demouser")).willReturn(Optional.of(UserProfile.builder().userId("demouser").build()));
		this.mockMvc.perform(get("/removeMedication/tylenol").sessionAttr("username", "demouser")).andExpect(status().is3xxRedirection());
		assertTrue(true, "should always be true");
	}

	@Test
	public void getMedDetailsNotLoggedIn() throws Exception {
		final LabelResults lrs = LabelResults.builder().version("1.0").openfda(LabelOpenfda.builder().brandName(List.of("Tylenol")).genericName(List.of("Motrin")).build())
				.description(List.of("Used as antibiotic")).adverseReactions(List.of("None")).build();
		given(medService.getDetails("tylenol")).willReturn(LabelResult.builder().results(List.of(lrs)).build());
		this.mockMvc.perform(get("/medicationDetails/tylenol")).andExpect(status().isOk()).andExpect(view().name("medicationDetails"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void getMedByPharmClassNotLoggedIn() throws Exception {
		this.mockMvc.perform(get("/addMedByPharmClass")).andExpect(status().is3xxRedirection());
		assertTrue(true, "should always be true");
	}

	@Test
	public void getMedByPharmClass() throws Exception {
		given(userService.findByUserId("demouser")).willReturn(Optional.of(UserProfile.builder().userId("demouser").build()));
		this.mockMvc.perform(get("/addMedByPharmClass").sessionAttr("username", "demouser")).andExpect(status().isOk()).andExpect(view().name("addMedByPharmClass"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void postMedByPharmClassNotLoggedIn() throws Exception {
		this.mockMvc.perform(post("/processAddMedByPharmClass")).andExpect(status().isOk()).andExpect(view().name("index"));
		assertTrue(true, "should always be true");
	}

	@Test
	public void postMedByPharmClass() throws Exception {
		given(userService.findByUserId("demouser")).willReturn(Optional.of(UserProfile.builder().userId("demouser").build()));
		this.mockMvc.perform(post("/processAddMedByPharmClass").sessionAttr("username", "demouser").param("selected", "tylenol")).andExpect(status().isOk())
				.andExpect(view().name("addMedByName"));
		assertTrue(true, "should always be true");
	}

}
