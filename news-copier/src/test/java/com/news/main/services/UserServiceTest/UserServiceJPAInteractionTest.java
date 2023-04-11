package com.news.main.services.UserServiceTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import com.news.main.repositories.UserRepository;
import com.news.main.services.UserService;

@SpringBootTest
public class UserServiceJPAInteractionTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void should_return_null_when_database_is_down() {
		doThrow(new RuntimeException("DataBase is down")).when(userRepository).findAll();
		doThrow(new RuntimeException("DataBase is down")).when(userRepository).save(any());
		doThrow(new RuntimeException("DataBase is down")).when(userRepository).deleteById(any());
		doThrow(new RuntimeException("DataBase is down")).when(userRepository).findByUsername(any());

		UserDetails loadUserByUsername2 = userService.findById(null);
		UserDetails loadUserByUsername3 = userService.findByUsername(null);
		userService.getAllUsers();
		userService.blockUserByIdAndSaveUser(null);
		userService.deleteUserById(null);
		userService.setRoleByUserIdAndSaveUser(null, null);
		userService.unblockUserByIdAndSaveUser(null);

		assertNull(loadUserByUsername2);
		assertNull(loadUserByUsername3);
	}

}
