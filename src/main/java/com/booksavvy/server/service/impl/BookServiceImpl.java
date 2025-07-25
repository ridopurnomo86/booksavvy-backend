package com.booksavvy.server.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booksavvy.server.config.RedisConfig;
import com.booksavvy.server.dto.book.BookCategoryResponse;
import com.booksavvy.server.entity.Book;
import com.booksavvy.server.repository.BookCategoriesRepository;
import com.booksavvy.server.repository.BookRepository;
import com.booksavvy.server.service.BookService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookCategoriesRepository bookCategoriesRepository;


    public BookServiceImpl(BookRepository bookRepository, RedisConfig redisConfig, BookCategoriesRepository bookCategoriesRepository) {
        this.bookRepository = bookRepository;
        this.bookCategoriesRepository = bookCategoriesRepository;
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Override
    public Optional<Book> getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) return book;
        
       return Optional.of(new Book()); 
    }

    @Override
    public List<BookCategoryResponse> getBookCategories() {
        List<BookCategoryResponse> categories = bookCategoriesRepository.findAll().stream().map(category -> new BookCategoryResponse(category.getId(), category.getName())).collect(Collectors.toList());
        
        return categories;
    }

}
