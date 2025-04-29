package com.homework.hogwartslibrary.domain;

import com.homework.hogwartslibrary.domain.BookType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class Book {
    private UUID id;
    private String title;
    private String author;
    private BigDecimal basePrice;
    private BookType type;
    private Integer stockQuantity;
    private Boolean available;
}
