package com.homework.hogwartslibrary.application.rest.book;

import com.homework.hogwartslibrary.domain.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Author must not be blank")
    private String author;

    @NotNull(message = "Base price must not be null")
    @Positive(message = "Base price must be positive")
    private Double basePrice;

    @NotNull(message = "Book type must not be null")
    private BookType type;
}
