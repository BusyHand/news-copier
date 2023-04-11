package com.news.main.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.news.main.services.UserService;
import com.news.main.validation.domain.RegistrationForm;

/**
 * Controller for registration users
 */
@Controller
@RequestMapping("/registration")
@SessionAttributes("form")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@ModelAttribute(name = "form")
	public RegistrationForm form() {
		return new RegistrationForm();
	}

	@GetMapping
	public String showRegister() {
		return "registration";
	}

	@PostMapping()
	public String processRegistrationJson(@ModelAttribute(name = "form") @Valid RegistrationForm form, Errors errors,
			SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "registration";
		}
		sessionStatus.setComplete();
		userService.processRegistrationAndSaveUser(form);
		return "login";
	}
}
