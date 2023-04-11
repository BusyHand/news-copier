package com.news.main.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.repositories.NewsRepository;
import com.news.main.services.around.AroundInterface;
import com.news.main.services.around.DaoClass;

/**
 * Service for work for news database
 */
@Service
public class NewsService implements ServiceProtocol<News> {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private CommentService commentService;

	private final <T> T execute(AroundInterface<T, NewsRepository> a) {
		return DaoClass.execute(a, newsRepository);
	}

	/**
	 * If you call this method from this class, use self injection bean
	 */
	@Override
	public News save(News news) {
		return execute(repo -> {
			return repo.save(news);
		});
	}

	/**
	 * If you call this method from this class, use self injection bean
	 */
	public void deleteNewsById(Long id) {
		/* Need to be removed due the cascade type */
		commentService.deleteCommentByNewsId(id);

		execute(repo -> {
			repo.deleteById(id);
			return null;
		});
	}

	public Map<Important, List<News>> getSubListNewsByImportance() {
		return execute(repo -> {
			Map<Important, List<News>> map = new HashMap<Important, List<News>>();
			Pageable pageable = PageRequest.of(0, 10);

			Important[] importance = Important.values();
			for (Important important : importance) {
				List<News> list = repo.findNewsByImportanceOrderByCreateAtDesc(important, pageable);
				map.put(important, list);
			}
			return map;
		});
	}

	public Iterable<News> getAllNews() {
		return execute(repo -> {
			return repo.findAll();
		});
	}

	public List<News> getAllOrderByDateNews() {
		return execute(repo -> {
			return repo.findAllByOrderByCreateAtDesc();
		});
	}

	public News getNewsById(Long id) {
		return execute(repo -> {
			return repo.getNewsById(id);
		});
	}

	public List<News> findNewsByTopics(Topic topic) {
		return execute(repo -> {
			return repo.findNewsByTopicsOrderByCreateAtDesc(topic);
		});
	}

	public List<News> findNewsByTopics(Topic topic, Pageable pageable) {
		return execute(repo -> {
			return repo.findNewsByTopicsOrderByCreateAtDesc(topic, pageable);
		});
	}

	public News findById(Long id) {
		return execute(repo -> {
			return repo.findById(id).get();
		});
	}

	public List<News> findByAuthor(String username) {
		return execute(repo -> {
			return repo.findAllByAuthor(username);
		});
	}

	/*
	 * For {@link com.news.main.controllers.ArchiveController} with unmodified list
	 */
	public List<News> searchNewsByHead(String head, Iterable<News> news) {
		List<News> searchNews = new ArrayList<News>();
		// Not blank request
		if (!head.isBlank()) {
			String headLower = head.trim().toLowerCase();
			List<News> list = (List<News>) news;
			list.forEach(post -> {
				if (post.getHead().toLowerCase(Locale.ROOT).contains(headLower)) {
					searchNews.add(post);
				}
			});
		}
		return searchNews;
	}

	/*
	 * For {@link com.news.main.controllers.AdminController} with modified list
	 */
	public void searchNewsByHead(String head, Iterable<News> news, List<News> searchNews) {
		searchNews.removeAll(searchNews);
		// Not blank request
		if (!head.isBlank()) {
			String headLower = head.trim().toLowerCase();
			List<News> list = (List<News>) news;
			list.forEach(post -> {
				if (post.getHead().toLowerCase(Locale.ROOT).contains(headLower)) {
					searchNews.add(post);
				}
			});
		}
	}

	public Map<String, Map<String, List<News>>> getMapAllNewsByYearAndMounth() {
		return getAllOrderByDateNews().stream()
				.collect(Collectors.groupingBy(News::getYearDate,
						Collectors.groupingBy(News::getMounthDate)));
	}

	public News getNewsEditPageById(Long id, User user) {
		News news = findById(id);
		if (user != null && !user.getAuthority().equals("ROLE_ADMIN")) {
			if (!news.getAuthor().equals(user.getUsername())) {
				return null;
			}
		}
		return news;
	}

	public News updateAndSaveNews(News news, List<String> imgs) {
		List<String> newWithOldImages = news.getImgs();
		imgs.forEach(img -> {
			if (!newWithOldImages.contains(img)) {
				newWithOldImages.add(img);
			}
		});
		return createAndSaveNews(news);
	}

	public News createAndSaveNews(News news) {
		news.setPreviewBody();
		return newsService.save(news);
	}

	public List<News> findNewsByTopicsForNewsPage(News newsById) {
		Pageable pageable = PageRequest.of(0, 7);
		List<News> anothersNewsByTopic = findNewsByTopics(newsById.getTopics(), pageable);
		if (anothersNewsByTopic != null) {
			if (anothersNewsByTopic.contains(newsById)) {
				anothersNewsByTopic.remove(newsById);
			} else {
				anothersNewsByTopic.remove(0);
			}
		}
		return anothersNewsByTopic;
	}

	public void setCommentNewsAndUserById(Long id, Comment comment, User user) {
		News news = getNewsById(id);
		comment.setNews(news);
		comment.setUser_table(user);
	}
}