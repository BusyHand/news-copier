package com.news.main.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogDatabaseAspect {

	@After("Pointcuts.allServiceSaveMethods()")
	public void afterSaveMethodsAdvice(JoinPoint joinPoint) {
		Object[] saveEntity = joinPoint.getArgs();
		for (Object entity : saveEntity) {
			logSaveEntity(entity);
		}
	}

	@Before("Pointcuts.allServiceDeleteMethods()")
	public void beforeDeleteMethodsAdvice(JoinPoint joinPoint) {
		Object[] entytiID = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		for (Object objectID : entytiID) {
			Long id = (Long) objectID;
			logDeleteEntity(methodName, id);
		}
	}

	private void logDeleteEntity(String methodName, Long id) {
		if (isCommentEntity(methodName, id))
			;
		else if (isNewsEntity(methodName, id))
			;
		else if (isUserEntity(methodName, id))
			;
	}

	private boolean isCommentEntity(String methodName, Long id) {
		if ("deleteCommentByUserId".equals(methodName)) {
			log.info("Delete comment by user_id: '{}'", id);
		} else if ("deleteCommentByNewsId".equals(methodName)) {
			log.info("Delete comment by news_id: '{}'", id);
		} else if ("deleteCommentById".equals(methodName)) {
			log.info("Delete comment by id: '{}'", id);
		} else {
			return false;
		}
		return true;
	}

	private boolean isNewsEntity(String methodName, Long id) {
		if ("deleteNewsById".equals(methodName)) {
			log.info("Delete news by id: '{}'", id);
		} else {
			return false;
		}
		return true;
	}

	private boolean isUserEntity(String methodName, Long id) {
		if ("deleteUserById".equals(methodName)) {
			log.info("Delete user by id: '{}'", id);
		} else {
			return false;
		}
		return true;
	}

	private void logSaveEntity(Object entity) {
		if (entity instanceof Comment) {
			Comment comment = (Comment) entity;
			log.info("Save Comment with id: '{}' and author: '{}'", comment.getId(), comment.getUser_table().getUsername());
		} else if (entity instanceof News) {
			News news = (News) entity;
			log.info("Save News with id: '{}' and head: '{}'", news.getId(), news.getHead());
		} else if (entity instanceof User) {
			User user = (User) entity;
			log.info("Save User with id: '{}' and username: '{}'", user.getId(), user.getUsername());
		}
	}
}
