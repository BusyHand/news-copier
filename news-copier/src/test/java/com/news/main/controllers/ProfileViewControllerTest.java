package com.news.main.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.domain.User;

@WebMvcTest(ProfileViewController.class)
@ContextConfiguration(classes = ControllersConfigurationTest.class)
@WithMockUser(authorities = "ROLE_ADMIN")
public class ProfileViewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void should1() throws Exception {
		this.mockMvc.perform(get("/profile/username")).andExpect(status().isOk());
	}

	@Test
	public void should2() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<>();
		sessionattr.put("user", new User("a", "a", "a", "a"));
		this.mockMvc.perform(get("/profile/showComments").sessionAttrs(sessionattr)).andExpect(status().isOk());

	}

	@Test
	public void should3() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<>();
		sessionattr.put("user", new User("a", "a", "a", "a"));
		this.mockMvc.perform(get("/profile/showPosts").sessionAttrs(sessionattr)).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(authorities = "ROLE_ADMIN")
	public void should4() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<>();
		sessionattr.put("user", new User("a", "a", "a", "a"));
		this.mockMvc.perform(get("/profile/delete/comment/1").sessionAttrs(sessionattr)).andExpect(status().isOk());
	}

}
