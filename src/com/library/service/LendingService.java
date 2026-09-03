package com.library.service;

import com.library.model.*;
import com.library.observer.PatronNotifier;

import java.util.Optional;
import java.util.logging.Logger;

public class LendingService {

    private final PatronManager patronManager;
    private final Library library;
    private static final Logger logger = Logger.getLogger(LendingService.class.getName());


    public LendingService(PatronManager patronManager, Library library) {
        this.patronManager = patronManager;
        this.library = library;
    }

    public void checkoutBook(String patronId, String isbn) {
        Optional<Book> bookOpt = library.findByIsbn(isbn);
        if (bookOpt.isEmpty()) {
            logger.warning("No book with ISBN " + isbn + " found in the library.");
            throw new IllegalArgumentException("No book with ISBN " + isbn + " found in the library.");
        }
        Book book = bookOpt.get();

        Optional<Patron> patronOpt = patronManager.findPatronById(patronId);
        if (patronOpt.isEmpty()) {
            logger.warning("No patron with ID " + patronId + " found.");
            throw new IllegalArgumentException("No patron with ID " + patronId + " found.");
        }
        Patron patron = patronOpt.get();

        if(book.getStatus() != BookStatus.AVAILABLE) {
            logger.warning("Book with ISBN " + isbn + " is not available for checkout.");
            throw new IllegalStateException("Book with ISBN " + isbn + " is not available for checkout.");
        }

        book.markAsBorrowed();
        patron.addToBorrowingHistory(book);
        logger.info("Book with ISBN " + isbn + " checked out to patron " + patronId + ".");
    }

    public void returnBook(String isbn) {
        // 1. find book, throw if empty
        Optional<Book> bookOpt = library.findByIsbn(isbn);
        if (bookOpt.isEmpty()) {
            logger.warning("No book with ISBN " + isbn + " found in the library.");
            throw new IllegalArgumentException("No book with ISBN " + isbn + " found in the library.");
        }
        Book book = bookOpt.get();

        // 2. check book.getStatus() == BookStatus.BORROWED, else throw
        if(book.getStatus() != BookStatus.BORROWED) {
            logger.warning("Book with ISBN " + isbn + " is not currently borrowed.");
            throw new IllegalStateException("Book with ISBN " + isbn + " is not currently borrowed.");
        }

        // 3. book.markAsAvailable();
        book.markAsAvailable();
        logger.info("Book with ISBN " + isbn + " has been returned.");
    }

    public void reserveBook(String patronId, String isbn){

        Optional<Book> bookOpt = library.findByIsbn(isbn);
        if (bookOpt.isEmpty()) {
            logger.warning("No book with ISBN " + isbn + " found in the library.");
            throw new IllegalArgumentException("No book with ISBN " + isbn + " found in the library.");
        }
        Book book = bookOpt.get();

        Optional<Patron> patronOpt = patronManager.findPatronById(patronId);
        if (patronOpt.isEmpty()) {
            logger.warning("No patron with ID " + patronId + " found.");
            throw new IllegalArgumentException("No patron with ID " + patronId + " found.");
        }
        Patron patron = patronOpt.get();

        if(book.getStatus() != BookStatus.BORROWED) {
            logger.warning("Book with ISBN " + isbn + " is not currently borrowed, cannot be reserved.");
            throw new IllegalStateException("Book with ISBN " + isbn + " is not currently borrowed, cannot be reserved.");
        }

        PatronNotifier notifier = new PatronNotifier(patron);
        book.addObserver(notifier);
        logger.info("Patron " + patronId + " has reserved book with ISBN " + isbn + ".");


    }

}
