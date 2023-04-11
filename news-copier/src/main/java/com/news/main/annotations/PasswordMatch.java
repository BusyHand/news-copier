package com.news.main.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.news.main.annotations.configuration.PasswordMatchValidator;

/**
 * Checks passwords for a match during registration.
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
	String message() default "{error.password.mismatch}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
