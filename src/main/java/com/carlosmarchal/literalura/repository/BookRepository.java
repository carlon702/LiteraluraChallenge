package com.carlosmarchal.literalura.repository;

import com.carlosmarchal.literalura.model.Book;
import com.carlosmarchal.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    List<Book> findAll();
    Optional<Book> findByTitleContainsIgnoreCase(String title);
    List<Book> findByLanguage(Language language);
}

