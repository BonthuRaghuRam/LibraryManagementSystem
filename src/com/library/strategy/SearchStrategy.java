package com.library.strategy;

import com.library.model.Book;

public interface SearchStrategy {
    boolean matches(Book book, String query);
}