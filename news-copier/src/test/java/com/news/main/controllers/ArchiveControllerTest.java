package com.news.main.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.domain.News;

@WebMvcTest(ArchiveController.class)
@ContextConfiguration(classes = ControllersConfigurationTest.class)
@WithMockUser(authorities = "ROLE_ADMIN")
public class ArchiveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should1() throws Exception {
		HashMap<String, Object> sessionattr1 = new HashMap<String, Object>();
		HashMap<String, Object> sessionattr2 = new HashMap<String, Object>();
		sessionattr1.put("map", new HashMap<String, Map<String, List<News>>>());
		sessionattr2.put("listNews", "");
		this.mockMvc.perform(get("/archive/mounth/2022").sessionAttrs(sessionattr1).sessionAttrs(sessionattr2))
				.andExpect(status().is3xxRedirection());

	}

	@Test
	public void should2() throws Exception {
		HashMap<String, Object> sessionattr1 = new HashMap<String, Object>();
		HashMap<String, Object> sessionattr2 = new HashMap<String, Object>();
		sessionattr1.put("map", new HashMap<String, Map<String, List<News>>>());
		sessionattr2.put("listNews", new ArrayList<News>());
		this.mockMvc.perform(post("/archive/search/news").with(csrf()).sessionAttrs(sessionattr1)
				.sessionAttrs(sessionattr2)).andExpect(status().is3xxRedirection());

	}
}
