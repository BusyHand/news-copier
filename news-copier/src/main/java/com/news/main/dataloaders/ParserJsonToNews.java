package com.news.main.dataloaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.news.main.domain.News;
import com.news.main.props.ParserNewsProps;
import com.news.main.props.RequestProps;

/**
 * Parse JSON objects to {@link com.news.main.domain.News} objects
 */
@Component
public class ParserJsonToNews {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ParserNewsProps parserNewsProps;

	@Autowired
	private ObjectMapper mapper;
	
	public Iterable<News> parseVKPostToNews() throws IOException {
		List<News> resultListNews = new ArrayList<>();
		for (RequestProps proper : parserNewsProps.getURLRequestsList()) {
			String jsonObject = restTemplateRequest(proper.getURLRequest());
			pasreVKJsonObject(resultListNews, proper, jsonObject);
		}
		return resultListNews;
	}

	private void pasreVKJsonObject(List<News> resultListNews, RequestProps proper, String jsonObject)
			throws JsonMappingException, JsonProcessingException {

		JsonNode array = getItemsArray(jsonObject);
		for (int i = 0; i < parserNewsProps.getCount(); i++) {
			JsonNode element = array.get(i);
			JsonVkJsonToNews vkPost = new JsonVkJsonToNews(parserNewsProps.getAuthorOfNewsFromVKGroups());
			News news = vkPost.getNewsWithSetsFields(element, proper);
			resultListNews.add(news);
		}
	}

	private JsonNode getItemsArray(String jsonObject) throws JsonMappingException, JsonProcessingException {
		JsonNode node = mapper.readTree(jsonObject);
		JsonNode child = node.get("response");
		return child.get("items");
	}

	private String restTemplateRequest(String request) {
		String jsonObject = restTemplate.getForObject(request, String.class);
		return jsonObject;
	}

}
