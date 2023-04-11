package com.news.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.main.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

}
