package com.news.main.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.news.main.domain.News;
import com.news.main.services.NewsService;

/**
 * Controller for searching news and sorting them by dates
 */
@Controller
@RequestMapping("/archive")
@SessionAttributes({ "map", "listNews" })
public class ArchiveController {

	@Autowired
	private NewsService newsService;

	@GetMapping
	public String showArchive() {
		return "archive";
	}

	@ModelAttribute("map")
	public Map<String, Map<String, List<News>>> getAllNewsMapedByYearAndMounth() {
		return newsService.getMapAllNewsByYearAndMounth();
	}

	/**
	 * Selects fresh news for the first entry to the page
	 */
	@ModelAttribute("listNews")
	public List<News> getSelectedNews(@ModelAttribute("map") Map<String, Map<String, List<News>>> map) {
		// Get newest news =)
		Iterator<Entry<String, Map<String, List<News>>>> iterator1 = map.entrySet().iterator();
		Entry<String, Map<String, List<News>>> first = iterator1.next();
		while (iterator1.hasNext()) {
			first = iterator1.next();
		}

		Iterator<Entry<String, List<News>>> iterator2 = first.getValue().entrySet().iterator();
		Entry<String, List<News>> second = iterator2.next();
		while (iterator2.hasNext()) {
			second = iterator2.next();
		}

		return second.getValue();
	}

	@GetMapping("/{mounth}/{year}")
	public String showListNews(@PathVariable("mounth") String mounth, @PathVariable("year") String year,
			@ModelAttribute("map") Map<String, Map<String, List<News>>> map, Model model) {
		List<News> listNews = map.get(year).get(mounth);
		model.addAttribute("listNews", listNews);
		return "redirect:/archive";
	}

	@PostMapping("/search/news")
	public String searchNewsByHead(String head, Model model) {
		List<News> searchNewsByHead = newsService.searchNewsByHead(head, newsService.getAllNews());
		model.addAttribute("listNews", searchNewsByHead);
		return "redirect:/archive";
	}

	@ExceptionHandler(NullPointerException.class)
	public String redirectOnIndex() {
		return "redirect:/";
	}

}
