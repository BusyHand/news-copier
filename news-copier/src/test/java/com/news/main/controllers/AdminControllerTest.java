package com.news.main.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = ControllersConfigurationTest.class)
@WithMockUser(authorities = "ROLE_ADMIN")
public class AdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should1() throws Exception {
		this.mockMvc.perform(get("/admin")).andExpect(status().isOk());
	}

	@Test
	public void should2() throws Exception {
		this.mockMvc.perform(get("/admin/delete/1")).andExpect(redirectedUrl("/admin"));
	}

	@Test
	public void should3() throws Exception {
		this.mockMvc.perform(get("/admin/block/1")).andExpect(redirectedUrl("/admin"));
	}

	@Test
	public void should4() throws Exception {
		this.mockMvc.perform(get("/admin/unblock/1")).andExpect(view().name("admin"));
	}

	@Test
	public void should5() throws Exception {
		this.mockMvc.perform(get("/admin/delete/news/1")).andExpect(view().name("admin"));
	}

	@Test
	public void should6() throws Exception {
		this.mockMvc.perform(get("/admin/role/1/ROLE_USER")).andExpect(view().name("admin"));
	}

	@Test
	public void should7() throws Exception {
		this.mockMvc.perform(get("/admin/role/1/ROLE_USER")).andExpect(view().name("admin"));
	}

	@Test
	public void should8() throws Exception {
		this.mockMvc.perform(post("/admin/search/user").with(csrf()).param("username", "ALESHA"))
				.andExpect(view().name("admin"));
	}

	@Test
	public void should9() throws Exception {
		this.mockMvc.perform(post("/admin/search/news").with(csrf()).param("username", "ALESHA"))
				.andExpect(view().name("admin"));
	}

}
