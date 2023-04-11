package com.news.main.props;

import com.news.main.enums.Topic;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestProps {
	private String URLRequest;
	private String source;
	private Topic topic;
}
