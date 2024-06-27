package com.carlosmarchal.literalura.repository;

import com.carlosmarchal.literalura.model.Author;

import com.carlosmarchal.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAll();
    Optional<Author> findByNameContainsIgnoreCase(String name);
//    @Query("SELECT a FROM Author a WHERE a.birthYear>=:initialYear AND a.deathYear<=:limitYear")
//    List<Author> getLivingAuthorsByYear(int initialYear, int limitYear);
    List<Author> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(int initialYear, int limitYear);
}
