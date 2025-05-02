package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

public class OldEditionPricingStrategy implements BookPricingStrategy {

    @Override
    public BigDecimal calculatePrice(final BookEntity book, final int totalBookQuantity) {

        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }

        final BigDecimal base = book.getBasePrice().multiply(BigDecimal.valueOf(0.8)); // 20% off

        if (totalBookQuantity >= 3) {
            return base.multiply(BigDecimal.valueOf(0.95)); // additional 5% off
        }
        return base;
    }
}
