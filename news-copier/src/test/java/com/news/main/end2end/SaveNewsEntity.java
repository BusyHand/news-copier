package com.news.main.end2end;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_ADMIN")
public class SaveNewsEntity {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NewsRepository newsRepository;

	private String jsonNews = "{\"head\":\"TITLE\"," + "\"importance\":\"MAIN\"," + "\"topics\":\"SPORT\","
			+ "\"sources\":\"sources\"," + "\"video\":\"video\"," + "\"body\":\"TESTING\"," + "\"author\":\"VK\"}";

	private String jsonUpdatedNews = "{\"head\":\"TITLE\"," + "\"importance\":\"MAIN\"," + "\"topics\":\"SPORT\","
			+ "\"sources\":\"sources\"," + "\"video\":\"video\"," + "\"body\":\"Updated News\",";
	
	@BeforeEach
	public void delete_all_data() {
		userRepository.deleteAll();
		newsRepository.deleteAll();
	}

	@Test
	public void should_persist_news() throws Exception {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		user.setAuthority("ROLE_ADMIN");
		userRepository.save(user);
		
		this.mockMvc.perform(
				post("/create").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(jsonNews))
				.andExpect(status().is3xxRedirection());

		News news = newsRepository.findAllByAuthor(user.getUsername()).get(0);

		assertEquals("TESTING", news.getBody());
	}

	@Test
	public void should_update_news() throws Exception {
		HashMap<String, Object> sessionattr = new HashMap<String, Object>();
		sessionattr.put("imgs", "XXXXXXXX");

		News beforeUpdateNews = new News(Important.MAIN, "TITLE", "BODY", "Sources", Topic.ECONOMICS, "VK");
		newsRepository.save(beforeUpdateNews);
		String content = jsonUpdatedNews + "\"id\":\"" + beforeUpdateNews.getId() + "\"}";

		this.mockMvc
				.perform(post("/edit/update").with(csrf()).sessionAttrs(sessionattr)
						.contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().is3xxRedirection());
		News updatedNews = newsRepository.findById(beforeUpdateNews.getId()).get();

		assertEquals("Updated News", updatedNews.getBody());
		assertEquals(beforeUpdateNews.getId(), updatedNews.getId());
	}
}
