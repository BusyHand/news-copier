package com.news.main.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.domain.User;
import com.news.main.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void should() throws Exception {
		User user = new User("username", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		userRepository.save(user);

		this.mockMvc.perform(formLogin("/login").user("username").password("password"))
				.andExpect(status().is3xxRedirection());

	}

}
