package com.library.model;

import com.library.observer.BookAvailabilityObserver;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String author;
    private final String isbn;
    private int publicationYear;
    private BookStatus status;
    private final List<BookAvailabilityObserver> observers;

    public Book(String title, String author, String isbn, int publicationYear) {
        validateTitle(title);
        validateAuthor(author);
        validateIsbn(isbn);
        validatePublicationYear(publicationYear);

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
        this.observers = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void setAuthor(String author) {
        validateAuthor(author);
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        validatePublicationYear(publicationYear);
        this.publicationYear = publicationYear;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank.");
        }
    }

    private static void validateAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be blank.");
        }
    }

    private static void validateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be blank.");
        }
    }

    private static void validatePublicationYear(int publicationYear) {
        int currentYear = Year.now().getValue();
        if (publicationYear < 0 || publicationYear > currentYear) {
            throw new IllegalArgumentException(
                    "Publication year must be between 0 and " + currentYear + ".");
        }
    }

    public BookStatus getStatus() {
        return status;
    }

    public void markAsBorrowed() {
        if (status == BookStatus.AVAILABLE) {
            status = BookStatus.BORROWED;
        } else {
            throw new IllegalStateException("Book cannot be borrowed. Current status: " + status);
        }
    }

    public void markAsAvailable() {
        if (status == BookStatus.BORROWED) {
            status = BookStatus.AVAILABLE;
            for (BookAvailabilityObserver observer : observers) {
                observer.onBookAvailable(this);
            }
        } else {
            throw new IllegalStateException("Book is already available. Current status: " + status);
        }
    }

    public void addObserver(BookAvailabilityObserver observer) {
        observers.add(observer);
    }
}