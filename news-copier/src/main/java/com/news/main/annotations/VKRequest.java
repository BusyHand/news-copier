package com.news.main.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.news.main.enums.Topic;

@Retention(RUNTIME)
@Target(FIELD)
public @interface VKRequest {
	String getURLRequest();

	String getSource();

	Topic getTopic();

}
