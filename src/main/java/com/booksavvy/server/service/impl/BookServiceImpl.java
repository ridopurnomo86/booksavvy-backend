package com.booksavvy.server.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booksavvy.server.entity.Book;
import com.booksavvy.server.repository.BookRepository;
import com.booksavvy.server.service.BookService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

}
