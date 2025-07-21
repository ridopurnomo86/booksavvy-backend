package com.booksavvy.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booksavvy.server.entity.BookCategory;

@Repository
public interface BookCategoriesRepository extends JpaRepository<BookCategory, Long> {
}
