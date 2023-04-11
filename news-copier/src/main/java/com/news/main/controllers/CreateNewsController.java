package com.news.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.main.domain.News;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.services.NewsService;

/**
 * Controller for create news
 */
@Controller
@RequestMapping("/create")
@PreAuthorize("hasAuthority('ROLE_ADMIN') || hasRole('ROLE_MANAGER')")
public class CreateNewsController {

	@Autowired
	private NewsService newsService;

	@ModelAttribute("importance")
	public List<String> showImportance() {
		List<String> list = new ArrayList<>();
		for (Important important : Important.values()) {
			list.add(important.toString());
		}
		return list;
	}

	@ModelAttribute("topics")
	public List<String> showTopic() {
		List<String> list = new ArrayList<>();
		for (Topic topic : Topic.values()) {
			list.add(topic.toString());
		}
		return list;
	}

	@ModelAttribute("news")
	public News showNews() {
		return new News();
	}

	@GetMapping
	public String show() {
		return "create";
	}

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String processingNewsUrlencoded(News news) {
		return processingNewsJson(news);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String processingNewsJson(@RequestBody News news) {
		newsService.createAndSaveNews(news);
		return "redirect:/news/" + news.getId();
	}


	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "redirect:/";
	}

}
