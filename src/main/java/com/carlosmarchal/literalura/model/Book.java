package com.carlosmarchal.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    @ManyToOne
    private Author author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Enumerated(EnumType.STRING)
    private Language language;
    private Integer downloadCount;

    public Book(){}

    public Book(BookRecord book, Author author){
        this.title = book.title();
        this.author = author;
        this.language = Language.getNameByCode(book.languages().get(0));
//        this.language = book.language().stream().map(Language::getNameByCode).collect(Collectors.toList()).get(0);
        this.downloadCount = book.downloadCount();
    }

    @Override
    public String toString() {
        return
                "\n Título: " + title +
                "\n Autor: " + author.toString() +
                "\n Idioma: " + language +
                "\n Número de descargas: " +downloadCount+
                "\n*_________________________________*";
    }
}
