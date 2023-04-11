package com.news.main.end2end;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.repositories.UserRepository;
import com.news.main.validation.domain.RegistrationForm;

@SpringBootTest
@AutoConfigureMockMvc
public class SaveUserEntity {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void delete_all_data() {
		userRepository.deleteAll();
	}

	@Test
	public void should_persist_user() throws Exception {
		RegistrationForm registrationForm = new RegistrationForm("username", "password", "password", "email", "phone");
		Map<String, Object> sessionattr = new HashMap<>();
		sessionattr.put("form", registrationForm);

		this.mockMvc.perform(post("/registration").with(csrf()).sessionAttrs(sessionattr)).andExpect(status().isOk());
		
		assertNotNull(userRepository.findByUsername("username"));
	}
}
