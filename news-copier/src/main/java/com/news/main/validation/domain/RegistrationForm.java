package com.news.main.validation.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.news.main.annotations.PasswordMatch;
import com.news.main.annotations.UniqueUserName;
import com.news.main.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form that comes when registering a new user and checks the correctness of
 * filling in the fields
 */
@Data
@PasswordMatch(message = "Confirm password should be the same.")
@NoArgsConstructor
@AllArgsConstructor()
public class RegistrationForm implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Username is required.")
	@UniqueUserName
	@Length(min = 4, max = 13, message = "Min 8, Max 25")
	private String username;

	@NotBlank(message = "Password is required.")
	@Length(min = 8, max = 25, message = "Min 8, Max 25")
	private String password;

	private String confirm;

	// @Email(message = "Email template does not match")
	@NotBlank(message = "Email is required.")
	private String email;

	// @Pattern(regexp = "8(?:-\\d{3}){2}(?:-\\d{2}){2}", message = "Phone is
	// required")
	@NotBlank(message = "Phone is required.")
	private String phone;

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), email, phone);
	}

}