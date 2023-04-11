package com.news.main.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.services.NewsService;


/**
 * Controller for editing already existing news
 */
@Controller
@RequestMapping("/edit")
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_MANAGER')")
@SessionAttributes({ "news", "deleteImgList", "imgs" })
public class EditNewsController {

	@Autowired
	private NewsService newsService;

	@GetMapping
	public String showEditNews() {
		return "editNews";
	}

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

	@ModelAttribute("deleteImgList")
	public List<String> deleteImgList() {
		return new ArrayList<>();
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
		News news = newsService.getNewsEditPageById(id, user);
		List<String> imgs = news.getImgs();
		model.addAttribute("imgs", imgs);
		model.addAttribute("news", news);
		return "editNews";
	}

	/**
	 * Method for deleting already existing images in the news
	 */
	@GetMapping("/delete")
	public String deleteImg(@RequestParam String img, @ModelAttribute("deleteImgList") List<String> deleteList,
			Model model) {
		deleteList.add(img);
		model.addAttribute("deleteImgList", deleteList);
		return "redirect:/edit/#imgs";
	}

	/**
	 * Method for returning images that should have been removed from the news
	 */
	@GetMapping("/back")
	public String backImg(@RequestParam String img, @ModelAttribute("deleteImgList") List<String> deleteList,
			Model model) {
		deleteList.remove(img);
		model.addAttribute("deleteImgList", deleteList);
		return "redirect:/edit/#imgs";
	}

	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateNewsJson(@RequestBody News editedNews, @ModelAttribute("deleteImgList") List<String> deleteList,
			@ModelAttribute("imgs") List<String> imgs, SessionStatus sessionStatus) {
		imgs.removeAll(deleteList);
		News updatedNews = newsService.updateAndSaveNews(editedNews, imgs);
		sessionStatus.setComplete();
		return "redirect:/news/" + updatedNews.getId();
	}

	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateNewsUrlencoded(News editedNews, @ModelAttribute("deleteImgList") List<String> deleteList,
			@ModelAttribute("imgs") List<String> imgs, SessionStatus sessionStatus) {
		return updateNewsJson(editedNews, deleteList, imgs, sessionStatus);
	}

	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "redirect:/";
	}

}
