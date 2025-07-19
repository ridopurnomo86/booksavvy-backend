package com.booksavvy.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booksavvy.server.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
