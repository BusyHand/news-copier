package com.news.main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.services.CommentService;
import com.news.main.services.NewsService;

/**
 * Controller for index page and show selected news
 */
@Controller
@RequestMapping("/")
@SessionAttributes({ "newsById", "topics", "comments", "anotherNewsByTopic", "errorMessge" })
public class NewsController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private CommentService commentService;

	@ModelAttribute("news")
	public List<News> showAllNews(Model model) {
		Map<Important, List<News>> map = newsService.getSubListNewsByImportance();

		List<News> list = new ArrayList<>();
		for (Important important : Important.values()) {
			list.addAll(map.get(important));
		}
		return list;
	}

	@ModelAttribute("topics")
	public List<String> showTopics() {
		List<String> list = new ArrayList<>();
		for (Topic topic : Topic.values()) {
			list.add(topic.toString());
		}
		return list;
	}

	@GetMapping
	public String showFeed() {
		return "index";
	}

	@GetMapping("/news")
	public String showNews() {
		return "news";
	}

	@GetMapping("/news/{id}")
	public String showSelectedNews(@PathVariable("id") Long id, Model model) {

		News newsById = newsService.getNewsById(id);
		model.addAttribute("newsById", newsById);


		List<News> findNewsByTopicsForNewsPage = newsService.findNewsByTopicsForNewsPage(newsById);
		model.addAttribute("anotherNewsByTopic", findNewsByTopicsForNewsPage);

		List<Comment> comments = commentService.findSubCommentsListByNewsId(id);
		model.addAttribute("comments", comments);

		return "redirect:/news";
	}

	@GetMapping("/showAllComments/news/{id}")
	public String showAllComments(@PathVariable("id") Long id, Model model) {
		List<Comment> comments = commentService.findAllCommentsByNewsId(id);
		model.addAttribute("comments", comments);

		return "redirect:/news";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/news/make/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createCommentJson(@PathVariable("id") Long id, @AuthenticationPrincipal User user,
			@RequestBody @Valid Comment comment, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessge", "Min 3, Max 100 symbols");
		} else {
			newsService.setCommentNewsAndUserById(id, comment, user);
			commentService.save(comment);
			model.addAttribute("errorMessge", "");
		}
		return "redirect:/news/" + id + "/#comment";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(path = "/news/make/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String createCommentUrlencoded(@PathVariable("id") Long id, @AuthenticationPrincipal User user,
			@Valid Comment comment, Errors errors, Model model) {
		return createCommentJson(id, user, comment, errors, model);
	}

	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "index";
	}

}
