package com.library.observer;

import com.library.model.Book;

public interface BookAvailabilityObserver {
    void onBookAvailable(Book book);
}