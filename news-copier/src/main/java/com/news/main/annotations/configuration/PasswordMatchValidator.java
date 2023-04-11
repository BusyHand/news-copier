package com.news.main.annotations.configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.news.main.annotations.PasswordMatch;
import com.news.main.validation.domain.RegistrationForm;

/**
 * Realization of {@link com.news.main.annotations.PasswordMatch} with
 * {@link com.news.main.validation.domain.RegistrationForm}
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegistrationForm> {

	@Override
	public void initialize(PasswordMatch p) {
	}

	@Override
	public boolean isValid(RegistrationForm form, ConstraintValidatorContext context) {
		String plainPassword = form.getPassword();
		String repeatPassword = form.getConfirm();

		if (plainPassword == null || !plainPassword.equals(repeatPassword)) {
			return false;
		}

		return true;
	}

}
