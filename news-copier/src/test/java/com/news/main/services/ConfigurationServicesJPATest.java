package com.news.main.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@TestConfiguration
public class ConfigurationServicesJPATest {

	@Bean
	@DependsOn({ "commentRepository" })
	public CommentService commentService() {
		return new CommentService();
	}

	@Bean
	@DependsOn({ "newsRepository", "commentRepository" })
	public NewsService newsService() {
		return new NewsService();
	}

	@Bean
	@DependsOn({ "commentRepository", "userRepository", "passwordEncoder" })
	public UserService userService() {
		return new UserService();
	}
}
