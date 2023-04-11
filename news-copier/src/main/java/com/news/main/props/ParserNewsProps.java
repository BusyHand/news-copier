package com.news.main.props;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.news.main.bpp.annotations.VKRequest;
import com.news.main.enums.Topic;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * Properties of requests
 */
@Component
@Data
@ConfigurationProperties(prefix = "parsernews.settings")
public class ParserNewsProps {

	private String authorOfNewsFromVKGroups = "FROM VK GROUP";

	private final int count = 3;

	// Secret tsss
	private final String access_token = "&access_token=d3e0676dd3e0676dd3e0676d8fd39c1c9fdd3e0d3e0676db19339468933cf2ab2aec2d0";
	
	private final String version = "&v=5.131";

	@VKRequest(
			getSource = "https://vk.com/javatutorial",
			getTopic = Topic.ENGINEERING,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=javatutorial&count=" + count + access_token
					+ version)
	private RequestProps javatutorial;

	@VKRequest(
			getSource = "https://vk.com/ru_post_punk",
			getTopic = Topic.ETC,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=ru_post_punk&count=" + count + access_token
					+ version)
	private RequestProps postPunk;

	@VKRequest(
			getSource = "https://vk.com/politrussiaru",
			getTopic = Topic.POLITICS,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=politrussiaru&count=" + count + access_token
					+ version)
	private RequestProps politRussiaru;

	@VKRequest(
			getSource = "https://vk.com/dailysports",
			getTopic = Topic.SPORT,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=dailysports&count=" + count + access_token
					+ version)
	private RequestProps dailysports;

	@VKRequest(
			getSource = "https://vk.com/economics_finance",
			getTopic = Topic.ECONOMICS,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=economics_finance&count=" + count + access_token
					+ version)
	private RequestProps economicsFinance;

	@VKRequest(
			getSource = "https://vk.com/educationgo",
			getTopic = Topic.EDUCATION,
			getURLRequest = "https://api.vk.com/method/wall.get?domain=educationgo&count=" + count + access_token
					+ version)
	private RequestProps educationgo;

	/**
	 * Return Request for RestTemplate
	 */
	@SneakyThrows
	public List<RequestProps> getURLRequestsList() {
		List<Field> allRequestPropsFields = getRequestPropsFields();
		List<RequestProps> URLRequests = new ArrayList<RequestProps>();
		for (Field field : allRequestPropsFields) {
			Object requestObj = field.get(this);
			RequestProps request = (RequestProps) requestObj;
			URLRequests.add(request);
		}

		return URLRequests;
	}

	private List<Field> getRequestPropsFields() {
		Field[] allFields = this.getClass().getDeclaredFields();
		List<Field> allRequestPropsFields = Arrays.asList(allFields).stream()
				.filter(f -> f.getType().equals(RequestProps.class)).collect(Collectors.toList());
		return allRequestPropsFields;
	}

}
