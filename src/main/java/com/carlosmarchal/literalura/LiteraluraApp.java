package com.carlosmarchal.literalura;

import com.carlosmarchal.literalura.app.App;
import com.carlosmarchal.literalura.repository.AuthorRepository;
import com.carlosmarchal.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApp.class, args);
	}

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Override
	public void run(String... args) throws Exception{
		App app = new App(bookRepository, authorRepository);
		app.showMenu();
	}
}
