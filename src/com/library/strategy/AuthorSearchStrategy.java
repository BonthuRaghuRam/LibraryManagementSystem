package com.library.strategy;

import com.library.model.Book;

public class AuthorSearchStrategy implements SearchStrategy {

    @Override
    public boolean matches(Book book, String query) {
        return book.getAuthor().toLowerCase().contains(query.toLowerCase());
    }


}
