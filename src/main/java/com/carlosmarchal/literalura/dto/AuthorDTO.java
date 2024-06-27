package com.carlosmarchal.literalura.dto;

import com.carlosmarchal.literalura.model.Book;

import java.util.List;

public record AuthorDTO(
        Long id,
        String author,
        Integer birthYear,
        Integer deathYear,
        List<Book> books
) {
}
