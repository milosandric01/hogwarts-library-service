package com.homework.hogwartslibrary.application.rest.book;

import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private BigDecimal basePrice;
    private BookType type;
    private boolean active;
    private LocalDateTime createdAt;

    public BookResponse(final BookEntity book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.basePrice = book.getBasePrice();
        this.type = book.getType();
        this.active = book.isActive();
        this.createdAt = book.getCreatedAt();
    }
}
