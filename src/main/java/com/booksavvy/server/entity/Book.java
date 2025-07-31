package com.booksavvy.server.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.auditing.AuditingHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum BookFormat {
    HARDCOVER,
    PAPERBACK,
    EBOOK,
    AUDIOBOOK,
    MAGAZINE,
    JOURNAL
}

@Entity
@Table(name = "book")
@EntityListeners(AuditingHandler.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "subtitle", nullable = false, length = 500)
    private String subtitle;

    @Column(name = "author", nullable = false, length = 1000)
    private String author;

    @Column(name = "co_authors", nullable = false, length = 1000)
    private String coAuthors;

    @Column(name = "description", nullable = false, length = 1000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "edition", length = 50)
    private String edition;

    @Column(name = "language", length = 50)
    @Builder.Default
    private String language = "English";

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "format")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BookFormat format = BookFormat.PAPERBACK;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "currency", length = 3)
    @Builder.Default
    private String currency = "USD";

    @Column(name = "quantity")
    @Builder.Default
    private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BookCategory category;
}


