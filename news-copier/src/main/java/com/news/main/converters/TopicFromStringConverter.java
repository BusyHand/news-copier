package com.news.main.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.news.main.enums.Topic;

/**
 * Converter for convert String to {@link com.news.main.enums.Topic}
 */
@Component
public class TopicFromStringConverter implements Converter<String, Topic> {

	@Override
	public Topic convert(String source) {
		for(Topic topic : Topic.values()) {
			if(topic.toString().equals(source)) {
				return topic;
			}
		}
		return Topic.ETC;
	}

	
	


	

}
