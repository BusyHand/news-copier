package com.news.main.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest()
@ContextConfiguration(classes = {ConfigurationServicesControllersTest.class, TestConfig.class})
public class ServicesJPAInteractionTest {

	@Autowired
	private NewsService newsService;
	
	@Test
	public void should_persist() {
		newsService.save(null);
	}

}
