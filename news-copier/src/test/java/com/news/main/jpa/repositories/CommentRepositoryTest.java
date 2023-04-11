package com.news.main.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
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
public class CommentRepositoryTest {

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
	public void should_delete_by_news_id() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment = new Comment("BODY", user, news);

		userRepository.save(user);
		News persistNews = newsRepository.save(news);
		commentRepository.save(comment);

		commentRepository.deleteByNewsId(persistNews.getId());
		assertEquals(Optional.empty(), commentRepository.findById(persistNews.getId()));
	}

	@Test
	public void should_delete_by_user_id() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment = new Comment("BODY", user, news);

		User persistUser = userRepository.save(user);
		newsRepository.save(news);
		commentRepository.save(comment);

		commentRepository.deleteByNewsId(persistUser.getId());
		assertEquals(Optional.empty(), commentRepository.findById(persistUser.getId()));
	}

	@Test
	public void should_find_all_comments_by_news_id() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment1 = new Comment("BODY1", user, news);
		Comment comment2 = new Comment("BODY2", user, news);

		userRepository.save(user);
		News persistNews = newsRepository.save(news);
		Comment persistComment1 = commentRepository.save(comment1);
		Comment persistComment2 = commentRepository.save(comment2);

		assertThat(List.of(persistComment1, persistComment2)
				.equals(commentRepository.findAllCommentsByNewsId(persistNews.getId())));
	}

	@Test
	public void should_find_all_comments_by_user_id() {
		User user = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");
		Comment comment1 = new Comment("BODY", user, news);
		Comment comment2 = new Comment("BODY", user, news);

		User persistUser = userRepository.save(user);
		newsRepository.save(news);
		Comment persistComment1 = commentRepository.save(comment1);
		Comment persistComment2 = commentRepository.save(comment2);

		assertThat(List.of(persistComment1, persistComment2)
				.equals(commentRepository.findAllCommentsByUserId(persistUser.getId())));

	}

	@Test
	public void should_find_all_comments_by_news_id_without_block() {
		User user1 = new User("VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");
		user1.setEnabled(false);
		User user2 = new User("NOT VK", new BCryptPasswordEncoder().encode("password"), "mail", "phone");

		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");

		Comment comment1 = new Comment("BODY", user1, news);
		Comment comment2 = new Comment("BODY", user2, news);
		Comment comment3 = new Comment("BODY", user2, news);

		userRepository.save(user1);
		userRepository.save(user2);
		News persistNews = newsRepository.save(news);

		commentRepository.save(comment1);
		Comment persistComment2 = commentRepository.save(comment2);
		Comment persistComment3 = commentRepository.save(comment3);

		assertThat(List.of(persistComment2, persistComment3)
				.equals(commentRepository.findAllCommentsByNewsIdWithoutBlockUser(persistNews.getId())));

	}

}
