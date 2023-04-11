package com.news.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.news.main.domain.Comment;
import com.news.main.repositories.CommentRepository;
import com.news.main.services.around.AroundInterface;
import com.news.main.services.around.DaoClass;

/**
 * Service for work for comments database
 */
@Service
public class CommentService implements ServiceProtocol<Comment> {

	@Autowired
	private CommentRepository commentRepository;

	private final <T> T execute(AroundInterface<T, CommentRepository> a) {
		return DaoClass.execute(a, commentRepository);
	}

	/**
	 * If you call this method from this class, use self injection bean
	 */
	@Override
	public Comment save(Comment comment) {
		return execute(repo -> {
			return repo.save(comment);
		});
	}

	/**
	 * if you use in method, method delete... you must add in name method verb
	 * 'delete' for log this operation
	 */
	public void deleteCommentById(Long id) {
		execute(repo -> {
			repo.deleteById(id);
			return null;
		});
	}

	public void deleteCommentByNewsId(Long id) {
		execute(repo -> {
			repo.deleteByNewsId(id);
			return null;
		});
	}

	public void deleteCommentByUserId(Long id) {
		execute(repo -> {
			repo.deleteByUserId(id);
			return null;
		});
	}

	/**
	 * Return sub list for short views comments
	 */
	public List<Comment> findSubCommentsListByNewsId(Long id) {
		return findAllCommentsByNewsId(id, PageRequest.of(0, 3));
	}

	public List<Comment> findAllCommentsByNewsId(Long id, Pageable... pageable) {
		return execute(repo -> {
			if (pageable.length == 0) {
				return repo.findAllCommentsByNewsIdWithoutBlockUser(id);

			} else
				return repo.findAllCommentsByNewsIdWithoutBlockUser(id, pageable[0]);
		});
	}

	public List<Comment> findAll() {
		return execute(repo -> {
			return (List<Comment>) repo.findAll();
		});
	}

	public List<Comment> findAllCommentsByUserId(Long id) {
		return execute(repo -> {
			return repo.findAllCommentsByUserId(id);
		});
	}

}
