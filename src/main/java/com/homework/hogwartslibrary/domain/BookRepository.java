package com.homework.hogwartslibrary.domain;

import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    BookEntity save(Book book);

    BookEntity update(UUID id, Book book);

    void delete(UUID id);

    Optional<BookEntity> findById(UUID id);

    List<BookEntity> fetchAvailable();

    Optional<BookEntity> fetch(UUID id);
}
