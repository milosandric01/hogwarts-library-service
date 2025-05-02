package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

public class NewReleasePricingStrategy implements BookPricingStrategy {

    @Override
    public BigDecimal calculatePrice(final BookEntity book, final int totalBookQuantity) {

        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }

        return book.getBasePrice();
    }
}
