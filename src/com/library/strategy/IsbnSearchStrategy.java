package com.library.strategy;

import com.library.model.Book;

public class IsbnSearchStrategy implements SearchStrategy{

    @Override
    public boolean matches(Book book, String query) {
        return book.getIsbn().equals(query);
    }
}
