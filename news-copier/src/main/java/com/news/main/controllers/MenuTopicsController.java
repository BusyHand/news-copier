package com.news.main.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.news.main.domain.News;
import com.news.main.enums.Topic;
import com.news.main.services.NewsService;

import lombok.AllArgsConstructor;

/**
 * Controller sorting news by topics
 */
@Controller
@AllArgsConstructor
@RequestMapping("/menu")
@SessionAttributes("topics")
public final class MenuTopicsController {

	private final NewsService newsService;

	@GetMapping
	public String showTopic() {
		return "topics";
	}

	@GetMapping("/{topic}")
	public String newsByTopicView(@PathVariable Topic topic, Model model) {
		List<News> list = newsService.findNewsByTopics(topic);
		model.addAttribute("news", list);
		return "topics";
	}

}
