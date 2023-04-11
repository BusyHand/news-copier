package com.news.main.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.news.main.domain.News;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;

public interface NewsRepository extends JpaRepository<News, Long> {

	News getNewsById(Long id);

	List<News> findNewsByTopicsOrderByCreateAtDesc(Topic topics);

	List<News> findNewsByTopicsOrderByCreateAtDesc(Topic topics, Pageable pageable);
	
	List<News> findAllByOrderByCreateAtDesc();
	
	List<News> findAllByAuthor(String username);
	
	List<News> findNewsByImportanceOrderByCreateAtDesc(Important important, Pageable pageable);
}
