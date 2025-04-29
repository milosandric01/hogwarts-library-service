package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.infrastructure.BookEntity;

import java.math.BigDecimal;

public class NewReleasePricingStrategy implements BookPricingStrategy {

    @Override
    public BigDecimal calculatePrice(BookEntity book, int totalBookQuantity) {
        return null;
    }
}
