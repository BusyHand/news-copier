package com.news.main.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@TestConfiguration
@MockBean(CommentRepository.class)
@MockBean(NewsRepository.class)
@MockBean(UserRepository.class)
public class ConfigurationServicesControllersTest {

	@Bean
	public CommentService commentService() {
		return new CommentService();
	}

	@Bean
	public NewsService newsService() {
		return new NewsService();
	}

	@Bean
	public UserService userService() {
		return new UserService();
	}
}
