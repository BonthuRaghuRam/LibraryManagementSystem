package com.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patron {

    private final String patronId;
    private String name;
    private String email;
    private final List<Book> borrowingHistory;

    public Patron(String patronId, String name, String email) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
    }

    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowingHistory() {
        return Collections.unmodifiableList(borrowingHistory);
    }

    public void addToBorrowingHistory(Book book) {
        borrowingHistory.add(book);
    }
}
