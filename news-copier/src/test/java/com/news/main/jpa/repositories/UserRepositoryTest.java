package com.news.main.jpa.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.news.main.domain.User;
import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

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
	public void should_find_by_username() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");

		User persistUser = userRepository.save(user);

		assertNotNull(userRepository.findByUsername(persistUser.getUsername()));
	}

}
