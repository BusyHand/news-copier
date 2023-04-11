package com.news.main.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.news.main.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Comment WHERE news_id = :id")
	void deleteByNewsId(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM Comment WHERE user_table_id = :id")
	void deleteByUserId(@Param("id") Long id);

	@Query("SELECT c FROM Comment c WHERE news_id = :id")
	List<Comment> findAllCommentsByNewsId(@Param("id") Long id);

	@Query("SELECT c FROM Comment c WHERE news_id = :id")
	List<Comment> findAllCommentsByNewsId(@Param("id") Long id, Pageable pageable);

	@Query("SELECT c FROM Comment c WHERE user_table_id = :id")
	List<Comment> findAllCommentsByUserId(@Param("id") Long id);

	@Query("SELECT c FROM Comment c WHERE user_table_id = :id")
	List<Comment> findAllCommentsByUserId(@Param("id") Long id, Pageable pageable);

	@Query("SELECT c FROM Comment c JOIN User u ON user_table_id = u.id WHERE u.enabled = true AND news_id = :id")
	List<Comment> findAllCommentsByNewsIdWithoutBlockUser(@Param("id") Long id);

	@Query("SELECT c FROM Comment c JOIN User u ON user_table_id = u.id WHERE u.enabled = true AND news_id = :id")
	List<Comment> findAllCommentsByNewsIdWithoutBlockUser(@Param("id") Long id, Pageable pageable);
}
