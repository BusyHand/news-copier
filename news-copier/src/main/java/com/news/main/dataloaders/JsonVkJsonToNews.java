package com.news.main.dataloaders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.news.main.domain.News;
import com.news.main.enums.Important;
import com.news.main.enums.Topic;
import com.news.main.props.RequestProps;

public class JsonVkJsonToNews {

	private News news;

	public JsonVkJsonToNews(String authorOfVkPosts) {
		this.news = new News();
		news.setAuthor(authorOfVkPosts);
	}

	public News getNewsWithSetsFields(JsonNode element, RequestProps proper) {
		setNewsFields(element, proper);
		return news;
	}

	public void setNewsFields(JsonNode element, RequestProps proper) {
		setDescription(element, proper);
		setAllContentAboutNews(element);
	}

	private void setDescription(JsonNode element, RequestProps proper) {
		setImportant();
		setTopic(proper);
		setSource(element, proper);
		setDate(element);
	}

	private void setAllContentAboutNews(JsonNode element) {
		setBody(element);
		setHeadFromBody();
		setPreviewBody();
		setImgs(element);

	}

	private void setBody(JsonNode element) {
		String body = element.get("text").asText();
		news.setBody(body);
	}

	private void setHeadFromBody() {
		String body = news.getBody();

		// Select the head from the news body
		Pattern pattern = Pattern.compile(".+?(\\.\\s|\\?|\\s\\s|\\!|:|$|\\()");
		Matcher matcher = pattern.matcher(body);

		String head;
		if (matcher.find()) {
			head = matcher.group();
		} else {
			head = "Interesting from " + news.getTopics();
		}

		if (body.equals(head)) {
			news.setBodyPresentOnPageNews(false);
		}

		news.setHead(head);
	}

	private void setPreviewBody() {
		news.setPreviewBody();
	}

	private void setSource(JsonNode element, RequestProps proper) {
		JsonNode ownerID = element.get("owner_id");
		JsonNode postID = element.get("id");
		news.setSources(proper.getSource() + "?w=wall" + ownerID + "_" + postID);
	}

	private void setTopic(RequestProps proper) {
		Topic topic = proper.getTopic();
		news.setTopics(topic);
	}

	private void setDate(JsonNode element) {
		JsonNode date = element.get("date");
		long timeStamp = date.asLong();
		news.setCreateAt(new Date(timeStamp * 1000));
	}

	private void setImportant() {
		news.setImportance(important());
	}

	private Important important() {
		int index = ThreadLocalRandom.current().nextInt(9);
		if (index <= 3)
			return Important.MAIN;
		else if (index <= 6)
			return Important.NEW;
		else
			return Important.ETC;
	}

	// TODO separete
	private void setImgs(JsonNode element) {
		List<String> imgs = new ArrayList<>();

		JsonNode attachmentsArray = element.get("attachments");

		if (element.has("attachments")) {
			for (int i = 0; attachmentsArray.has(i); i++) {
				JsonNode elementOfAttachments = attachmentsArray.get(i);

				if (elementOfAttachments.has("photo")) {
					JsonNode photoObj = elementOfAttachments.get("photo");
					JsonNode sizeArray = photoObj.get("sizes");

					setImgsSize(imgs, sizeArray);
				}
			}
		}
		news.setImgs(imgs);
	}

	private void setImgsSize(List<String> imgs, JsonNode sizeArray) {
		for (int j = 0; sizeArray.has(j); j++) {
			JsonNode elementOfSizes = sizeArray.get(j);
			String typeImg = elementOfSizes.get("type").asText();
			if (typeImg.equals("w")) {
				String urlImg = elementOfSizes.get("url").asText();
				imgs.add(urlImg);
				break;
			} else if (typeImg.equals("z")) {
				String urlImg = elementOfSizes.get("url").asText();
				imgs.add(urlImg);
				break;
			} else if (typeImg.equals("y")) {
				String urlImg = elementOfSizes.get("url").asText();
				imgs.add(urlImg);
				break;
			} else if (typeImg.equals("x")) {
				String urlImg = elementOfSizes.get("url").asText();
				imgs.add(urlImg);
				break;
			}
		}
	}

}
