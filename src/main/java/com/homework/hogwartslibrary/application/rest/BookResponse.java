package com.homework.hogwartslibrary.application.rest;

import com.homework.hogwartslibrary.domain.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private BigDecimal basePrice;
    private BookType type;
    private Integer stockQuantity;
    private Boolean available;
}
