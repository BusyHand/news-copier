package com.news.main.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EditNewsController.class)
@ContextConfiguration(classes = ControllersConfigurationTest.class)
@WithMockUser(authorities = "ROLE_ADMIN")
public class EditNewsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should1() throws Exception {
		this.mockMvc.perform(get("/edit/1")).andExpect(view().name("redirect:/"));
	}

	@Test
	public void should2() throws Exception {
		this.mockMvc.perform(get("/edit/delete").param("img", "")).andExpect(view().name("redirect:/edit/#imgs"));
	}

	@Test
	public void should3() throws Exception {
		this.mockMvc.perform(get("/edit/back").param("img", "")).andExpect(view().name("redirect:/edit/#imgs"));
	}

	@Test
	public void should4() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("imgs", "XXXXXXXX");
		this.mockMvc.perform(post("/edit/update").with(csrf()).sessionAttrs(sessionattr));

	}
}
