package com.homework.hogwartslibrary.domain.inventory;

import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    BookEntity addBook(String title, String author, double basePrice, BookType type, int stockQuantity);

    BookEntity updateBook(UUID id, String title, String author, double basePrice, BookType type, int stockQuantity);

    void removeBook(UUID id);

    List<BookEntity> getAvailableBooks();
}
