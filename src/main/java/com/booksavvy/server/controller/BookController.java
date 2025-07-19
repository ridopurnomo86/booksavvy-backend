package com.booksavvy.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booksavvy.server.dto.common.Response;
import com.booksavvy.server.entity.Book;
import com.booksavvy.server.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {
    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public Response books() {
        List<Book> books = bookService.getBooks();

        Response response = new Response("success", "success", "success", books);

        return response;
    }

    @GetMapping("/{id}")
    public Response getBook(@PathVariable String id) {
        Optional<Book> book = bookService.getBook(Long.parseLong(id));
        
        Response response = new Response("success", "success", "success", book);

        return response;
    }
    
}
