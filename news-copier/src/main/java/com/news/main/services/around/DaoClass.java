package com.news.main.services.around;

import lombok.extern.slf4j.Slf4j;

/**
 * Component for connecting services with repositories and handle exception when
 * database is down
 */
@Slf4j
public class DaoClass {

	public static final <T, R> T execute(AroundInterface<T, R> a, R repo) {
		try {
			return a.ecxecute(repo);
		} catch (Throwable e) {
			log.error("Database error: {}", e);
			return null;
		}
	}
}
