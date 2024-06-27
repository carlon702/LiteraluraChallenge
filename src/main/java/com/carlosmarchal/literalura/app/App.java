package com.carlosmarchal.literalura.app;

import com.carlosmarchal.literalura.model.*;
import com.carlosmarchal.literalura.repository.AuthorRepository;
import com.carlosmarchal.literalura.repository.BookRepository;
import com.carlosmarchal.literalura.service.ObjectConversor;
import com.carlosmarchal.literalura.service.RequestAPI;
import com.fasterxml.jackson.core.JsonProcessingException;


import javax.swing.text.html.Option;
import java.util.*;

public class App {

    private final Scanner sc = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books";
    private final ObjectConversor conversor = new ObjectConversor();
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public App(BookRepository bookRepo, AuthorRepository authorRepo){
        this.bookRepository = bookRepo;
        this.authorRepository = authorRepo;
    }



    public void showMenu() throws JsonProcessingException {
        var option = -1;
        while(option != 0) {
            System.out.println("""
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos por año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);
            option = sc.nextInt();
            sc.nextLine();

            switch (option){
                case 1:
                    saveBook();
                    break;
                case 2:
                    showRegisteredBooks();
                    break;
                case 3:
                    showRegisteredAuthors();
                    break;
                case 4:
                    showLivingAuthors();
                    break;
                case 5:
                    showBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion...");
                    System.exit(0);
                default:
                    System.out.println("Opcion invalida!");
            }

        }
    }

    private String searchBook(){
        System.out.println("Escribe el nombre del libro que quieres buscar:");
        String bookName = sc.nextLine();
        return RequestAPI.getData(URL_BASE+ "/?search=" + bookName.replace(" ","%20"));
    }

    private void saveBook(){
        String data = searchBook();
        List<BookRecord> booksFound = conversor.getData(data, ResultRecord.class).results().stream().limit(5).toList();
        BookRecord selectedBook = showBooks(booksFound);
        Author insertionAuthor = new Author(selectedBook.authors().get(0));
        Book insertionBook = new Book(selectedBook,insertionAuthor);


        if(!checkAuthorDb(insertionAuthor)){
            authorRepository.save(insertionAuthor);
            System.out.println("Author successfully registered!");
        } else {
            System.out.println("Author already registered.");
        }

        if(!checkBookDb(insertionBook)){
            bookRepository.save(insertionBook);
            System.out.println("Book successfully registered!");
        } else {
            System.out.println("Book already registered.");
        }




    }

    private BookRecord showBooks(List<BookRecord> bookRecords){
        bookRecords.forEach((b)-> System.out.println("Book Title:" + b.title() +"\nAuthor:" + b.authors() + "\nLanguage:"+b.languages()+"\nDownload:"+b.downloadCount()+"\n*-----------------------*"));
        System.out.println("Please pick which book you want to register(1-5):");
        int chosenNumber = sc.nextInt()-1;
        return bookRecords.get(chosenNumber);
    }

    private boolean checkBookDb(Book bookToInsert){
        Optional<Book> repeatedBook=bookRepository.findByTitleContainsIgnoreCase(bookToInsert.getTitle());
        return repeatedBook.isPresent();
    }

    private boolean checkAuthorDb(Author authorToInsert){
        Optional<Author> repeatedAuthor = authorRepository.findByNameContainsIgnoreCase(authorToInsert.getName());
        return repeatedAuthor.isPresent();
    }

    private void showRegisteredBooks(){
        List<Book> registeredBooks = bookRepository.findAll();
        registeredBooks.forEach(b-> System.out.println(b.toString()));
    }

    private void showRegisteredAuthors(){
        List<Author> registeredAuthors = authorRepository.findAll();
        registeredAuthors.forEach(a-> System.out.println(a.toString()));
    }

    private void showLivingAuthors(){
        System.out.println("Por favor ingrese el año inicial:");
        int initialYear = sc.nextInt();
        System.out.println("Por favor ingrese el año limite:");
        int endYear = sc.nextInt();
        List<Author> livingAuthors = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(initialYear, endYear);

        if(livingAuthors.isEmpty()){
            System.out.println("No se encontraron autores vivos en ese rango.");
        } else {
            livingAuthors.forEach(a-> System.out.println(a.toString()));
        }
    }

    private void showBooksByLanguage(){
        System.out.println("""
                Menú de opciones:
                1. Inglés
                2. Español
                3. Frances
                4. Portugués
                5. Italiano
                6. Alemán
                7. Ruso
                8. Chino
                """);
        System.out.print("Selecciona el número del idioma de los libros que deseas obtener: ");
        int inputLanguage = sc.nextInt();
        Language selectedLanguage = null;

        switch (inputLanguage){
            case 1:
                selectedLanguage = Language.ENGLISH;
                break;
            case 2:
                selectedLanguage = Language.SPANISH;
                break;
            case 3:
                selectedLanguage = Language.FRENCH;
                break;
            case 4:
                selectedLanguage =  Language.PORTUGUESE;
                break;
            case 5:
                selectedLanguage = Language.ITALIAN;
                break;
            case 6:
                selectedLanguage = Language.GERMAN;
                break;
            case 7:
                selectedLanguage = Language.RUSSIAN;
                break;
            case 8:
                selectedLanguage = Language.CHINESE;
                break;
            default:
                System.out.println("Opcion invalida");
        }


        List<Book> booksByLanguage = bookRepository.findByLanguage(selectedLanguage);

        if(booksByLanguage.isEmpty()){
            System.out.println("No se han econtrado libros con el lenguaje seleccionado.");
        } else {
            booksByLanguage.forEach(b-> System.out.println(b.toString()));
        }

    }

}
