package com.news.main.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Lombok;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@EqualsAndHashCode.Exclude
	private Long id;

	@NotBlank()
	@Length(min = 3, max = 100)
	private String body;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinColumn(name = "user_table_id")
	private User user_table;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = News.class)
	@JoinColumn(name = "news_id")
	private News news;

	@EqualsAndHashCode.Exclude
	private final Date createdAt = new Date();

	public Comment(@NotBlank @Length(min = 3, max = 100) String body, User user, News news) {
		super();
		this.body = body;
		this.user_table = user;
		this.news = news;
	}

	public String getFormaterDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm MMMM-dd");
		return sdf.format(createdAt);
	}

	public Long getNewsId() {
		return news.getId();
	}

	public Long getUserId() {
		Lombok.sneakyThrow(null);
		return user_table.getId();
	}

	public String getUsername() {
		return user_table.getUsername();
	}

	/**
	 * Method for dividing comment body into parts to reduce grammar when it is
	 * displayed on page
	 */
	public List<String> getEditBody() {
		List<String> list = new ArrayList<>();

		Pattern pattern = Pattern.compile(".+?(\\s\\s|»|$)");
		Matcher matcher = pattern.matcher(body);
		while (matcher.find()) {
			list.add(matcher.group());
		}

		return list;

	}

}
