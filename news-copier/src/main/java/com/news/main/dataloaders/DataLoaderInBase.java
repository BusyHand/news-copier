package com.news.main.dataloaders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.news.main.domain.Comment;
import com.news.main.domain.News;
import com.news.main.domain.User;
import com.news.main.repositories.CommentRepository;
import com.news.main.repositories.NewsRepository;
import com.news.main.repositories.UserRepository;

@Configuration
@Profile("!test")
public class DataLoaderInBase {

	@Bean
	CommandLineRunner dataLoader(ParserJsonToNews parser, UserRepository userRepo,
			PasswordEncoder passwordEncoder,
			NewsRepository newsRepo, CommentRepository commentRepository) {
		return args -> {
			User admin = new User("a", passwordEncoder.encode("a"), "mail", "7-99-00");
			admin.setAuthority("ROLE_ADMIN");
			userRepo.save(admin);

			// ALL Post from VK will have this author
			User authorOfVkPosts = new User("FROM VK GROUP", passwordEncoder.encode("2o3ujtoiegnklasmdk"), "VK POST",
					"VK POST");
			authorOfVkPosts.setAuthority("ROLE_MANAGER");
			userRepo.save(authorOfVkPosts);
			
			for (News news : parser.parseVKPostToNews()) {
				Comment comment = new Comment();
				comment.setBody("ГОВНО");
				comment.setUser_table(admin);
				comment.setNews(news);
				newsRepo.save(news);
				commentRepository.save(comment);
			}
		};
	}

}
