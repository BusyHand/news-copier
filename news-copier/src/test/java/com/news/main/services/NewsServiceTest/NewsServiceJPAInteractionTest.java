package com.news.main.services.NewsServiceTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.news.main.domain.News;
import com.news.main.enums.Important;
import com.news.main.repositories.NewsRepository;
import com.news.main.services.NewsService;

@SpringBootTest
public class NewsServiceJPAInteractionTest {

	@Autowired
	private NewsService newsService;

	@MockBean
	private NewsRepository newsRepository;

	@Test
	public void should_return_null_when_database_is_down() {
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).findAll();
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).getNewsById(any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).save(any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).findById(any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository)
				.findNewsByTopicsOrderByCreateAtDesc(any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository)
				.findNewsByTopicsOrderByCreateAtDesc(any(), any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository)
				.findNewsByImportanceOrderByCreateAtDesc(any(), any());
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).findAllByOrderByCreateAtDesc();
		doThrow(new RuntimeException("DataBase is down")).when(newsRepository).findAllByAuthor(any());

		Map<Important, List<News>> allNewsByImportance = newsService.getSubListNewsByImportance();
		Iterable<News> allNews = newsService.getAllNews();
		List<News> allOrderByDateNews = newsService.getAllOrderByDateNews();
		News newsById = newsService.getNewsById(null);
		List<News> findNewsByTopics = newsService.findNewsByTopics(null);
		List<News> findNewsByTopics2 = newsService.findNewsByTopics(null, null);
		News saveNews = newsService.save(null);
		News findById = newsService.findById(null);
		newsService.deleteNewsById(null);
		List<News> findByAuthor = newsService.findByAuthor(null);
		List<News> findNewsByTopicsForNewsPage = newsService.findNewsByTopicsForNewsPage(new News());
		News newsEditPageById = newsService.getNewsEditPageById(null, null);

		assertNull(allNewsByImportance);
		assertNull(allNews);
		assertNull(allOrderByDateNews);
		assertNull(newsById);
		assertNull(findNewsByTopics);
		assertNull(findNewsByTopics2);
		assertNull(saveNews);
		assertNull(findById);
		assertNull(findByAuthor);
		assertNull(findNewsByTopicsForNewsPage);
		assertNull(newsEditPageById);
	}

}
