package com.news.main.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CreateNewsController.class)
@ContextConfiguration(classes = ControllersConfigurationTest.class)
@WithMockUser(authorities = "ROLE_ADMIN")
public class CreateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private String jsonNews = "{\"head\":\"TITLE\"," + "\"importance\":\"MAIN\"," + "\"topics\":\"SPORT\","
			+ "\"sources\":\"sources\"," + "\"video\":\"video\"," + "\"body\":\"TESTING\"}";

	@Test
	public void should1() throws Exception {
		this.mockMvc.perform(get("/create")).andExpect(status().isOk());
	}

	@Test
	public void should2() throws Exception {
		this.mockMvc.perform(post("/create").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonNews))
				.andExpect(status().is3xxRedirection());
	}
}
