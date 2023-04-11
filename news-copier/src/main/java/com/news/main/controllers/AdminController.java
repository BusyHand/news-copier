package com.news.main.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.services.NewsService;
import com.news.main.services.UserService;

/**
 * Controller for news and user administration
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private NewsService newsService;

	@GetMapping
	public String show() {
		return "admin";
	}

	@ModelAttribute("users")
	public Iterable<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@ModelAttribute("news")
	public Iterable<News> getAllNews() {
		return newsService.getAllNews();
	}

	@ModelAttribute("searchUsers")
	public Iterable<User> getSearchUsers() {
		return new ArrayList<>();
	}

	@ModelAttribute("searchNews")
	public Iterable<News> getSearchNews() {
		return new ArrayList<>();
	}

	@GetMapping("/delete/{id}")
	public String deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@GetMapping("/block/{id}")
	public String blockUserById(@PathVariable("id") Long id) {
		userService.blockUserByIdAndSaveUser(id);
		return "redirect:/admin";
	}

	@GetMapping("/unblock/{id}")
	public String unblockUserById(@PathVariable("id") Long id) {
		userService.unblockUserByIdAndSaveUser(id);
		return "admin";
	}

	@GetMapping("/delete/news/{id}")
	public String deleteNewsById(@PathVariable("id") Long id) {
		newsService.deleteNewsById(id);
		return "admin";
	}

	@GetMapping("/role/{id}/{role}")
	public String setUserRole(@PathVariable("id") Long id, @PathVariable("role") String role) {
		userService.setRoleByUserIdAndSaveUser(id, role);
		return "admin";
	}

	@PostMapping("/search/user")
	public String searchUserByUsername(String username, @ModelAttribute("users") Iterable<User> users,
			@ModelAttribute("searchUsers") List<User> searchUsers) {
		userService.searchUserByUsername(username, users, searchUsers);
		return "admin";
	}

	@PostMapping("/search/news")
	public String searchNewsByHead(String head, @ModelAttribute("news") Iterable<News> news,
			@ModelAttribute("searchNews") List<News> searchNews) {
		newsService.searchNewsByHead(head, news, searchNews);
		return "admin";
	}

	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "index";
	}

}
