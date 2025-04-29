package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

public interface BookPricingStrategy {
    BigDecimal calculatePrice(BookEntity book, int totalBookQuantity);
}
