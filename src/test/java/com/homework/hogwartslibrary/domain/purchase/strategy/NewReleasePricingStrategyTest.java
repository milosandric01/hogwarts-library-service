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
class NewReleasePricingStrategyTest {

    @InjectMocks
    private NewReleasePricingStrategy strategy;

    @Test
    void calculatePrice_givenBookNull_shouldThrowException() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                strategy.calculatePrice(null, 2)
        );

        assertEquals("Book must not be null", exception.getMessage());
    }

    @Test
    void calculatePrice_givenCorrectParams_shouldNeverApplyDiscount() {
        final BookEntity book = mock(BookEntity.class);

        when(book.getBasePrice()).thenReturn(BigDecimal.valueOf(100.00));

        BigDecimal result = strategy.calculatePrice(book, 5); // totalBookQuantity is ignored for new releases

        assertEquals(BigDecimal.valueOf(100.00).setScale(2), result.setScale(2));
    }
}