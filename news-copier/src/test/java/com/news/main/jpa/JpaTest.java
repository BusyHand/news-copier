package com.news.main.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@SpringBootTest
public class JpaTest {

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
	public void should_persit_and_find() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment = new Comment("BODY", user, news);

		User persistUser = userRepository.save(user);
		News persistNews = newsRepository.save(news);
		Comment persistComment = commentRepository.save(comment);

		assertNotNull(userRepository.findById(persistUser.getId()));
		assertNotNull(newsRepository.findById(persistNews.getId()));
		assertNotNull(commentRepository.findById(persistComment.getId()));
	}

	@Test
	public void should_delete() {

		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment = new Comment("BODY", user, news);

		User persistUser = userRepository.save(user);
		News persistNews = newsRepository.save(news);
		Comment persistComment = commentRepository.save(comment);

		commentRepository.delete(persistComment);
		userRepository.delete(persistUser);
		newsRepository.delete(persistNews);

		assertEquals(Optional.empty(), userRepository.findById(persistUser.getId()));
		assertEquals(Optional.empty(), newsRepository.findById(persistNews.getId()));
		assertEquals(Optional.empty(), commentRepository.findById(persistComment.getId()));
	}

	@Test
	public void should_update() {

		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment = new Comment("BODY", user, news);

		User persistUser = userRepository.save(user);
		News persistNews = newsRepository.save(news);
		Comment persistComment = commentRepository.save(comment);

		// Update
		persistUser.setAuthority("ROLE_ADMIN");
		persistNews.setBody("NEW BODY");
		persistComment.setBody("NEW BODY");

		User updateUser = userRepository.save(persistUser);
		News updateNews = newsRepository.save(persistNews);
		Comment updateComment = commentRepository.save(persistComment);

		assertEquals(updateUser.getId(), userRepository.findById(persistUser.getId()).get().getId());
		assertEquals(updateNews.getId(), newsRepository.findById(persistNews.getId()).get().getId());
		assertEquals(updateComment.getId(), commentRepository.findById(persistComment.getId()).get().getId());
	}

}
