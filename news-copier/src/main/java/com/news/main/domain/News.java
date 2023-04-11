package com.news.main.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.news.main.enums.Important;
import com.news.main.enums.Topic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Exclude
	private Long id;

	private Important importance;

	@Column(columnDefinition = "TEXT")
	private String head;

	@Column(columnDefinition = "TEXT")
	private String body;

	private String sources;

	private Topic topics;

	private String author;

	private String video;

	@EqualsAndHashCode.Exclude
	private String previewBody;

	@EqualsAndHashCode.Exclude
	private Date createAt = new Date();

	private boolean isBodyPresentOnPageNews = true;

	@Column
	@ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
	private List<String> imgs = new ArrayList<>();

	public News(Important importance, String head, String body, String sources, Topic topics, String author) {
		super();
		this.importance = importance;
		this.head = head;
		this.body = body;
		this.sources = sources;
		this.topics = topics;
		this.author = author;
	}

	/**
	 * This is needed because I can't make delimited text in FRONTEND and can't save
	 * List<String> body because Heroku (deployment server) gives database row limit
	 */
	public List<String> getEditBody() {
		List<String> list = new ArrayList<>();
		String parseText;
		if (body.length() >= head.length() && head.equals(body.substring(0, head.length()))) {
			parseText = body.substring(head.length()).trim();
		} else {
			parseText = body;
		}
		Pattern pattern = Pattern.compile(".+?(\\s\\s|$)");
		Matcher matcher = pattern.matcher(parseText);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		addTextSnippetToEmbedVideo(list);

		return list;

	}

	public void setPreviewBody() {
		if (head.length() < body.length()) {
			if (body.length() - head.length() <= 150) {
				previewBody = body.substring(head.length(), body.length());
			} else {
				previewBody = body.substring(head.length(), head.length() + 146) + "...";
			}
		}
	}

	private void addTextSnippetToEmbedVideo(List<String> list) {
		if (body.length() > 150) {
			list.add(list.size() / 2, "  |VIDEO PART|  ");
		} else {
			list.add("  |VIDEO PART|  ");
		}
	}

	public void addImg(String img) {
		imgs.add(img);
	}

	public void deleteImg(String fileName) {
		if (imgs.contains(fileName)) {
			imgs.remove(fileName);
		}
	}

	public void addAllImgs(List<String> addAllImgs) {
		for (String img : addAllImgs) {
			if (!imgs.contains(img)) {
				imgs.add(img);
			}
		}
	}

	public void deleteListImgs(List<String> deleteImgs) {
		for (String img : deleteImgs) {
			imgs.remove(img);
		}
	}

	public String getFormaterDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMMM-dd");
		return sdf.format(createAt);
	}

	public String getYearDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		return sdf.format(createAt);

	}

	public String getMounthDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		return sdf.format(createAt);

	}

	public boolean listImgsIsNotEmpty() {
		return !imgs.isEmpty();
	}

	public String getFirstImg() {
		return imgs.get(0);
	}

	public List<String> getWithoutFirstImg() {
		return imgs.subList(1, imgs.size());
	}

	public boolean isImgsSizeOne() {
		return imgs.size() == 1;
	}

	public boolean isVideoNotEmpty() {
		if (video != null) {
			return !video.isBlank();
		}
		return false;
	}

	public void setVideo(String video) {
		// receives : https://www.youtube.com/watch?v=qPPD2YUwGnQ&t=3367s
		// return : https://www.youtube.com/embed/qPPD2YUwGnQ&t=3367s?rel=0
		Pattern pattern = Pattern.compile("((v=).+?($|\\&|\\?))|((be/watch\\?v=).+?($|\\&|\\?))|((be/).+?($|\\&|\\?))");
		Matcher matcher = pattern.matcher(video);

		if (matcher.find()) {
			String group = matcher.group();

			if (group.contains("be/watch?v=")) {
				group = group.substring(11);
			}
			if (group.contains("v=")) {
				group = group.substring(2);
			}
			if (group.contains("be/")) {
				group = group.substring(3);
			}

			if (group.contains("&") || group.contains("?")) {
				group = group.substring(0, group.length() - 1);
			}

			this.video = "https://www.youtube.com/embed/" + group + "?rel=0";
		}

	}

}
