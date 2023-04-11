package com.news.main.end2end;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SaveCommentEntity {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NewsRepository newsRepository;
	
	@BeforeEach
	public void delete_all_data() {
		commentRepository.deleteAll();
		userRepository.deleteAll();
		newsRepository.deleteAll();
	}


	@Test
	public void should_persist_comment() throws Exception {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		userRepository.save(user);
		newsRepository.save(news);

		this.mockMvc
				.perform(post("/news/make/" + news.getId()).with(csrf()).with(user(user))
						.contentType(MediaType.APPLICATION_JSON).content("{\"body\":\"TESTING\"}"))
				.andExpect(status().is3xxRedirection());

		assertEquals("TESTING", commentRepository.findAllCommentsByNewsId(news.getId()).get(0).getBody());
	}

}
