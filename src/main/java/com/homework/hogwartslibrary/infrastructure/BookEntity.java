package com.homework.hogwartslibrary.infrastructure;

import com.homework.hogwartslibrary.domain.BookType;
import lombok.RequiredArgsConstructor;
import org.jooq.Record2;
import org.jooq.generated.tables.records.BookRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class BookEntity {

    private final BookRecord bookRecord;

    public UUID getId() {
        return UUID.fromString(bookRecord.getId());
    }

    public String getTitle() {
        return bookRecord.getTitle();
    }

    public String getAuthor() {
        return bookRecord.getAuthor();
    }

    public BigDecimal getBasePrice() {
        return bookRecord.getBasePrice();
    }

    public BookType getType() {
        return BookType.valueOf(bookRecord.getType());
    }

    public boolean isActive() {
        return bookRecord.getActive() != null && bookRecord.getActive() != 0;
    }

    public LocalDateTime getCreatedAt() {
        return bookRecord.getCreatedAt();
    }
}
