package com.news.main.services;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.news.main.domain.User;
import com.news.main.repositories.UserRepository;
import com.news.main.services.around.AroundInterface;
import com.news.main.services.around.DaoClass;
import com.news.main.validation.domain.RegistrationForm;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for work for user database
 */
@Service
@Slf4j
public class UserService implements ServiceProtocol<User> {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentService commentService;

	private final <T> T execute(AroundInterface<T, UserRepository> a) {
		return DaoClass.execute(a, userRepository);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * If you call this method from this class, use self injection bean
	 */
	@Override
	public User save(User user) {
		return execute(repo -> {
			return repo.save(user);
		});
	}

	/**
	 * If you call this method from this class, use self injection bean
	 */
	public void deleteUserById(Long id) {
		/* Need to be removed due the cascade type */
		commentService.deleteCommentByUserId(id);

		execute(repo -> {
			repo.deleteById(id);
			return null;
		});
	}

	public User processRegistrationAndSaveUser(RegistrationForm form) {
		User user = form.toUser(passwordEncoder);
		return userService.save(user);
	}

	public Iterable<User> getAllUsers() {
		return execute(repo -> {
			return repo.findAll();
		});
	}

	public User findById(Long id) {
		return execute(repo -> {
			return repo.findById(id).get();
		});
	}

	public User setRoleByUserIdAndSaveUser(Long id, String role) {
		return execute(repo -> {
			User user = repo.findById(id).get();
			user.setAuthority(role);
			log.info("Set new role '{}' to user '{}' ", role, user.getUsername());
			return userService.save(user);
		});

	}

	public User findByUsername(String author) {
		return execute(repo -> {
			return repo.findByUsername(author);
		});
	}

	public User blockUserByIdAndSaveUser(Long id) {
		return execute(repo -> {
			User user = repo.findById(id).get();
			user.setEnabled(false);
			log.info("Block user '{}' by id '{}' ", user.getUsername(), user.getId());
			return userService.save(user);
		});

	}

	public User unblockUserByIdAndSaveUser(Long id) {
		return execute(repo -> {
			User user = repo.findById(id).get();
			user.setEnabled(true);
			log.info("Unblock user '{}' by id '{}' ", user.getUsername(), user.getId());
			return userService.save(user);
		});
	}

	public void searchUserByUsername(String username, Iterable<User> users, List<User> searchUsers) {
		searchUsers.removeAll(searchUsers);
		// Not blank request
		if (!username.isBlank()) {
			String usernameLower = username.trim().toLowerCase();
			List<User> list = (List<User>) users;
			list.forEach(user -> {
				if (user.getUsername().toLowerCase(Locale.ROOT).contains(usernameLower)) {
					searchUsers.add(user);
				}
			});
		}
	}
}
