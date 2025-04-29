package com.homework.hogwartslibrary.infrastructure.relational;

import com.homework.hogwartslibrary.domain.Book;
import com.homework.hogwartslibrary.domain.BookRepository;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.records.BookRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.generated.tables.Book.BOOK;

@Repository
@RequiredArgsConstructor
public class RelationalBookRepository implements BookRepository {

    private final DSLContext dslContext;

    @Override
    public BookEntity save(final Book book) {
        final String id = UUID.randomUUID().toString();

        dslContext.insertInto(BOOK, BOOK.ID, BOOK.AUTHOR, BOOK.TITLE, BOOK.TYPE,
                        BOOK.BASE_PRICE, BOOK.ACTIVE)
                .values(id, book.getAuthor(), book.getTitle(), book.getType().name(), book.getBasePrice(), (byte) 1)
                .execute();

        // Why? As Im using MySql and it does not support insert...returning
        // which means that i need to do additional fetch to get the data.
        // This is not too bad as its fetching by ID which is index, so its fast.
        final BookRecord savedBook = dslContext.selectFrom(BOOK)
                .where(BOOK.ID.eq(id))
                .fetchOne();

        return new BookEntity(savedBook);
    }

    @Override
    public BookEntity update(final UUID id, final Book book) {
        final byte isActive = (byte) (book.isActive() ? 1 : 0);

        dslContext.update(BOOK)
                .set(BOOK.TITLE, book.getTitle())
                .set(BOOK.AUTHOR, book.getAuthor())
                .set(BOOK.TYPE, book.getType().name())
                .set(BOOK.BASE_PRICE, book.getBasePrice())
                .set(BOOK.ACTIVE, isActive)
                .where(BOOK.ID.eq(id.toString()))
                .execute();

        // Why? As Im using MySql and it does not support insert...returning
        // which means that i need to do additional fetch to get the data.
        // This is not too bad as its fetching by ID which is index, so its fast.
        final BookRecord savedBook = dslContext.selectFrom(BOOK)
                .where(BOOK.ID.eq(id.toString()))
                .fetchOne();

        return new BookEntity(savedBook);
    }

    @Override
    public void delete(final UUID id) {
        dslContext.update(BOOK)
                .set(BOOK.ACTIVE, (byte) 0)
                .where(BOOK.ID.eq(id.toString()))
                .execute();
    }

    @Override
    public Optional<BookEntity> findById(final UUID id) {
        return Optional.ofNullable(
                dslContext.selectFrom(BOOK)
                        .where(BOOK.ID.eq(id.toString()))
                        .fetchOne()
        ).map(BookEntity::new);
    }

    @Override
    public List<BookEntity> fetchAvailable() {
        return dslContext.selectFrom(BOOK)
                .where(BOOK.ACTIVE.eq((byte) 1))
                .orderBy(BOOK.CREATED_AT.desc())
                .stream()
                .map(BookEntity::new)
                .toList();
    }

    @Override
    public Optional<BookEntity> fetch(final UUID id) {
        return Optional.ofNullable(
                dslContext.selectFrom(BOOK)
                        .where(BOOK.ID.eq(id.toString()))
                        .fetchOne()
        ).map(BookEntity::new);
    }
}
