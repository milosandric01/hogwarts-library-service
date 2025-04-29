package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

import static com.homework.hogwartslibrary.domain.BookType.NEW_RELEASE;

public class NewReleasePricingStrategy implements BookPricingStrategy {

    @Override
    public BigDecimal calculatePrice(final BookEntity book, final int totalBookQuantity) {

        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }

        return book.getBasePrice();
    }
}
