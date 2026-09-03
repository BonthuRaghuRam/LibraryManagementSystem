package com.library.console;

import com.library.model.*;
import com.library.service.LendingService;
import com.library.strategy.TitleSearchStrategy;

import java.util.List;

public class LibraryConsoleApp {
    private final Library library;
    private final PatronManager patronManager;
    private final LendingService lendingService;
    private final ConsoleInputReader input;

    public LibraryConsoleApp(Library library, PatronManager patronManager,
                             LendingService lendingService, ConsoleInputReader input) {
        this.library = library;
        this.patronManager = patronManager;
        this.lendingService = lendingService;
        this.input = input;
    }

    public void run() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Checkout Book");
            System.out.println("2. Return Book");
            System.out.println("3. Add Book");
            System.out.println("4. Search Book by Title");
            System.out.println("5. Add Patron");
            System.out.println("6. Reserve Book");
            System.out.println("7. Show Inventory");
            System.out.println("8. Exit");
            int choice = input.readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    String patronId = input.readNonBlank("Enter Patron ID: ");
                    String isbn = input.readNonBlank("Enter Book ISBN: ");
                    try {
                        lendingService.checkoutBook(patronId, isbn);
                        System.out.println("Book checked out successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    String returnIsbn = input.readNonBlank("Enter Book ISBN: ");
                    try {
                        lendingService.returnBook(returnIsbn);
                        System.out.println("Book returned successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    String title = input.readNonBlank("Enter Title: ");
                    String author = input.readNonBlank("Enter Author: ");
                    String newIsbn = input.readNonBlank("Enter ISBN: ");
                    int year = input.readYear("Enter Publication Year: ");
                    try {
                        library.addBook(new Book(title, author, newIsbn, year));
                        System.out.println("Book added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    String query = input.readNonBlank("Enter title to search: ");
                    List<Book> results = library.search(new TitleSearchStrategy(), query);
                    if (results.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        for (Book b : results) {
                            System.out.println(b.getIsbn() + " | " + b.getTitle() + " | "
                                    + b.getAuthor() + " | " + b.getStatus());
                        }
                    }
                    break;

                case 5:
                    String newPatronId = input.readNonBlank("Enter Patron ID: ");
                    String name = input.readNonBlank("Enter Name: ");
                    String email = input.readNonBlank("Enter Email: ");
                    try {
                        patronManager.addPatron(new Patron(newPatronId, name, email));
                        System.out.println("Patron added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    String reservingPatronId = input.readNonBlank("Enter Patron ID: ");
                    String reserveIsbn = input.readNonBlank("Enter Book ISBN: ");
                    try {
                        lendingService.reserveBook(reservingPatronId, reserveIsbn);
                        System.out.println("Book reserved successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    List<Book> available = library.getAvailableBooks();
                    List<Book> borrowed = library.getBorrowedBooks();
                    System.out.println("Available (" + available.size() + "):");
                    for (Book b : available) {
                        System.out.println("  " + b.getIsbn() + " | " + b.getTitle());
                    }
                    System.out.println("Borrowed (" + borrowed.size() + "):");
                    for (Book b : borrowed) {
                        System.out.println("  " + b.getIsbn() + " | " + b.getTitle());
                    }
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}