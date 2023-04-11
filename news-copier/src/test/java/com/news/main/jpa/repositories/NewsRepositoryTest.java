package com.news.main.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.news.main.domain.News;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@SpringBootTest
public class NewsRepositoryTest {

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
	public void should_find_by_id_and_get() {
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");

		News persistNews = newsRepository.save(news);

		assertNotNull(newsRepository.findById(persistNews.getId()));
	}

	@Test
	public void should_find_news_by_topics() {
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");

		News persistNews = newsRepository.save(news);

		assertNotNull(newsRepository.findNewsByTopicsOrderByCreateAtDesc(persistNews.getTopics()));

	}

	@Test
	public void should_find_news_by_importance() {
		News news = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "VK");

		News persistNews = newsRepository.save(news);

		assertNotNull(newsRepository.findNewsByImportanceOrderByCreateAtDesc(persistNews.getImportance(), null));

	}

	@Test
	public void should_find_all() {
		News news1 = new News(Important.MAIN, "Title1", "Body1", "Sources1", Topic.ECONOMICS, "VK");
		News news2 = new News(Important.MAIN, "Title2", "Body2", "Sources2", Topic.ECONOMICS, "VK");

		News persistNews1 = newsRepository.save(news1);
		News persistNews2 = newsRepository.save(news2);

		assertThat(List.of(persistNews1, persistNews2).equals(newsRepository.findAll()));

	}

	@Test
	public void should_find_all_by_author() {
		News news1 = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "AUTHOR");
		News news2 = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "AUTHOR");
		News news3 = new News(Important.MAIN, "Title", "Body", "Sources", Topic.ECONOMICS, "NOT AUTHOR");

		News persistNews1 = newsRepository.save(news1);
		News persistNews2 = newsRepository.save(news2);
		newsRepository.save(news3);

		assertThat(List.of(persistNews1, persistNews2).equals(newsRepository.findAllByAuthor("AUTHOR")));

	}
}
