package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

import static com.homework.hogwartslibrary.domain.BookType.REGULAR;

public class RegularPricingStrategy implements BookPricingStrategy {

    @Override
    public BigDecimal calculatePrice(final BookEntity book, final int totalBookQuantity) {

        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }

        if (totalBookQuantity >= 3) {
            return book.getBasePrice().multiply(BigDecimal.valueOf(0.9));
        }
        return book.getBasePrice();
    }
}
