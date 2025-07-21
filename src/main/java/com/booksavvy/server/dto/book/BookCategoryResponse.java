package com.booksavvy.server.dto.book;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookCategoryResponse {
    private Long id;
    private String name;

    public BookCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
