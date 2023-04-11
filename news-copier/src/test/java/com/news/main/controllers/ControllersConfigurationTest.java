package com.news.main.controllers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.news.main.repositories.UserRepository;
import com.news.main.services.CommentService;
import com.news.main.services.NewsService;
import com.news.main.services.UserService;

@TestConfiguration
@MockBean(NewsService.class)
@MockBean(CommentService.class)
@MockBean(UserService.class)
@MockBean(UserRepository.class) //UserDatailsService require bean
public class ControllersConfigurationTest {

}
