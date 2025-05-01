package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.infrastructure.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OldEditionPricingStrategyTest {

    @InjectMocks
    private OldEditionPricingStrategy strategy;

    @Test
    void calculatePrice_givenBookNull_shouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.calculatePrice(null, 3)
        );

        assertEquals("Book must not be null", exception.getMessage());
    }

    @Test
    void calculatePrice_givenQuantityLessThanThree_shouldApply20pDiscount() {
        final BookEntity book = mock(BookEntity.class);

        when(book.getBasePrice()).thenReturn(BigDecimal.valueOf(100.00));

        BigDecimal price = strategy.calculatePrice(book, 2);

        assertEquals(BigDecimal.valueOf(80.00).setScale(2), price.setScale(2));
    }

    @Test
    void calculatePrice_givenQuantityBiggerThanThree_shouldApply20pDiscountPlusAdditional5p() {
        final BookEntity book = mock(BookEntity.class);

        when(book.getBasePrice()).thenReturn(BigDecimal.valueOf(100.00));

        BigDecimal price = strategy.calculatePrice(book, 3);

        // 100 * 0.8 * 0.95 = 76
        assertEquals(BigDecimal.valueOf(76.00).setScale(2), price.setScale(2));
    }
}