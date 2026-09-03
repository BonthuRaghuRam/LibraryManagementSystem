package com.library.observer;

import com.library.model.Book;
import com.library.model.Patron;

public class PatronNotifier implements BookAvailabilityObserver {

    private final Patron patron;

    public PatronNotifier(Patron patron) {
        this.patron = patron;
    }

    @Override
    public void onBookAvailable(Book book) {
        System.out.println("Notification: " + patron.getName() + ", the book '" + book.getTitle() + "' is now available.");
    }
}