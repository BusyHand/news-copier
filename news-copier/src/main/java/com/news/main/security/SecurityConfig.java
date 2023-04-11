package com.news.main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.news.main.domain.User;
import com.news.main.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http.authorizeRequests().anyRequest().permitAll()

				.and().formLogin().loginPage("/login")

				.and().logout().logoutSuccessUrl("/")

				.and().build();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			User user = userRepo.findByUsername(username);

			if (user != null) {
				String authority = user.getAuthority();
				if ("ROLE_MANAGER".equals(authority) || "ROLE_ADMIN".equals(authority)) {
					logImportantUser(user);
				}
				return user;
			}
			throw new UsernameNotFoundException("User '" + username + "' not found");
		};
	}

	private final void logImportantUser(User user) {
		log.info("User '{}' with '{}' has in", user.getUsername(), user.getAuthority());
	}

}