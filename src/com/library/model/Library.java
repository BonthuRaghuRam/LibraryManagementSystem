package com.library.model;

import com.library.strategy.SearchStrategy;
import java.util.logging.Logger;

import java.util.*;

public class Library {
    private final Map<String, Book> books;
    private static final Logger logger = Logger.getLogger(Library.class.getName());

    public Library() {
        this.books = new HashMap<>();
    }

    public void addBook(Book book){
        if (books.containsKey(book.getIsbn())) {
            logger.warning("A book with ISBN " + book.getIsbn() + " already exists in the library.");
            throw new IllegalArgumentException("A book with ISBN " + book.getIsbn() + " already exists in the library.");
        }
        books.put(book.getIsbn(), book);
        logger.info("Book with ISBN " + book.getIsbn() + " has been added to the library.");
    }

    public void removeBook(String isbn){
        if (!books.containsKey(isbn)) {
            logger.warning("No book with ISBN " + isbn + " found in the library.");
            throw new IllegalArgumentException("No book with ISBN " + isbn + " found in the library.");
        }
        books.remove(isbn);
        logger.info("Book with ISBN " + isbn + " has been removed from the library.");
    }

    public void updateBook(String isbn, String title, String author, int publicationYear){
        if (!books.containsKey(isbn)) {
            logger.warning("No book with ISBN " + isbn + " found in the library.");
            throw new IllegalArgumentException("No book with ISBN " + isbn + " found in the library.");
        }
        Book book = books.get(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
        logger.info("Book with ISBN " + isbn + " has been updated.");
    }

    public Optional<Book> findByIsbn(String isbn) {
        Book book = books.get(isbn);       // returns null if not in the map
        return Optional.ofNullable(book);  // wraps it: present if non-null, empty if null
    }

    public List<Book> search(SearchStrategy strategy, String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (strategy.matches(book, query)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Book> getBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getStatus() == BookStatus.BORROWED) {
                borrowedBooks.add(book);
            }
        }
        return borrowedBooks;
    }
}
