package com.news.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.services.CommentService;
import com.news.main.services.NewsService;
import com.news.main.services.UserService;


/**
 * Controller profile page
 */
@Controller
@RequestMapping("/profile")
@SessionAttributes("user")
public class ProfileViewController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@GetMapping
	public String showTopic() {
		return "profile";
	}

	@GetMapping("/{username}")
	public String show(@PathVariable("username") String username, Model model) {
		User user = userService.findByUsername(username);
		if (user == null) {
			return "index";
		}
		model.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/showPosts")
	public String showPosts(@ModelAttribute("user") User user, Model model) {
		List<News> list = newsService.findByAuthor(user.getUsername());
		if (list == null) {
			return "index";
		}
		model.addAttribute("posts", list);
		return "profile";
	}

	@GetMapping("/showComments")
	public String showComments(@ModelAttribute("user") User user, Model model) {
		List<Comment> comments = commentService.findAllCommentsByUserId(user.getId());
		if (comments == null) {
			return "index";
		}
		model.addAttribute("comments", comments);
		return "profile";
	}

	@GetMapping("/delete/comment/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String showComments(@PathVariable("id") Long id) {
		commentService.deleteCommentById(id);
		return "profile";
	}

	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "index";
	}

}
