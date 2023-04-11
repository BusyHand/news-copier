package com.news.main.services.CommentServiceTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.news.main.domain.Comment;
import com.news.main.repositories.CommentRepository;
import com.news.main.services.CommentService;

@SpringBootTest
public class CommentServiceJPAInteractionTest {

	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	public void should_return_null_when_database_is_down() {
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository).findAll();
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository).save(any());
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository).deleteById(any());
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository)
				.findAllCommentsByNewsIdWithoutBlockUser(any());
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository)
				.findAllCommentsByNewsIdWithoutBlockUser(any(), any());
		doThrow(new RuntimeException("DataBase is down")).when(commentRepository).findAllCommentsByUserId(any());

		List<Comment> findAll = commentService.findAll();
		Comment save = commentService.save(null);
		commentService.deleteCommentById(null);
		List<Comment> findAllCommentsByNewsId = commentService.findAllCommentsByNewsId(null);
		List<Comment> findSubCommentsListByNewsId = commentService.findSubCommentsListByNewsId(null);
		List<Comment> findAllCommentsByUserId = commentService.findAllCommentsByUserId(null);

		assertNull(findAll);
		assertNull(save);
		assertNull(findAllCommentsByNewsId);
		assertNull(findSubCommentsListByNewsId);
		assertNull(findAllCommentsByUserId);
	}

}
