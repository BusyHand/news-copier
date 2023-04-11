package com.news.main.services.around;

/**
 * Functional interface for handle exception when database is down. With pattern
 * execute around
 */
@FunctionalInterface
public interface AroundInterface<T, R> {
	T ecxecute(R repo);
}
