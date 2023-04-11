package com.news.main.annotations.configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.news.main.annotations.UniqueUserName;
import com.news.main.domain.User;
import com.news.main.services.UserService;

/**
 * Realization of {@link com.news.main.annotations.UniqueUserName}
 */
public class UserNameValidator implements ConstraintValidator<UniqueUserName, String> {

	@Autowired
	private UserService userService;

	@Override
	public void initialize(UniqueUserName p) {
		
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		User findUser = userService.findByUsername(username);
		if (findUser == null) {
			return true;
		}
		return false;
	}

}
