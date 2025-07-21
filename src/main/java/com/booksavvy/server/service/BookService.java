package com.booksavvy.server.service;

import java.util.List;
import java.util.Optional;

import com.booksavvy.server.dto.book.BookCategoryResponse;
import com.booksavvy.server.entity.Book;

public interface BookService {
    List<Book> getBooks();
    Optional<Book> getBook(Long id);
    List<BookCategoryResponse> getBookCategories();
} 
