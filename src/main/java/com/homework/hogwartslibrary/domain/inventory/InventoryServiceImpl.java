package com.homework.hogwartslibrary.domain.inventory;

import com.homework.hogwartslibrary.domain.Book;
import com.homework.hogwartslibrary.domain.BookRepository;
import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final BookRepository bookRepository;

    @Override
    public BookEntity addBook(final String title, final String author, final double basePrice, final BookType type, final int stockQuantity) {

        final Book book = Book.builder()
                .title(title)
                .author(author)
                .basePrice(BigDecimal.valueOf(basePrice))
                .type(type)
                .stockQuantity(stockQuantity)
                .available(stockQuantity > 0)
                .build();

        return bookRepository.save(book);
    }

    @Override
    public BookEntity updateBook(UUID id, String title, String author, double basePrice, BookType type, int stockQuantity) {
        final UUID bookId = bookRepository.findById(id).map(BookEntity::getId).orElseThrow();

        final Book book = Book.builder().title(title)
                .author(author)
                .basePrice(BigDecimal.valueOf(basePrice))
                .type(type)
                .stockQuantity(stockQuantity)
                .available(stockQuantity > 0)
                .build();

        return bookRepository.update(bookId, book);
    }

    @Override
    public void removeBook(final UUID id) {
        final UUID bookId = bookRepository.findById(id).map(BookEntity::getId).orElseThrow();

        bookRepository.delete(bookId);
    }

    @Override
    public List<BookEntity> getAvailableBooks() {
        return bookRepository.fetchAvailable();
    }
}
