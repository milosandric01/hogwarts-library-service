package com.homework.hogwartslibrary.domain.service;

import com.homework.hogwartslibrary.domain.Book;
import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface InventoryService {
    void addBook(String title, String author, double basePrice, BookType type, int stockQuantity);

    void updateBook(UUID id, String title, String author, double basePrice, BookType type, int stockQuantity);

    void removeBook(UUID id);

    List<BookEntity> getAvailableBooks();
}
