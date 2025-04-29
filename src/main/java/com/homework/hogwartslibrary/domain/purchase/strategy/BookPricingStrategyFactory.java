package com.homework.hogwartslibrary.domain.purchase.strategy;

import com.homework.hogwartslibrary.domain.BookType;
import com.homework.hogwartslibrary.domain.purchase.strategy.BookPricingStrategy;
import com.homework.hogwartslibrary.domain.purchase.strategy.NewReleasePricingStrategy;
import com.homework.hogwartslibrary.domain.purchase.strategy.OldEditionPricingStrategy;
import com.homework.hogwartslibrary.domain.purchase.strategy.RegularPricingStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.homework.hogwartslibrary.domain.BookType.*;

@Component
public class BookPricingStrategyFactory {
    private final Map<BookType, BookPricingStrategy> strategyMap;

    public BookPricingStrategyFactory() {
        strategyMap = Map.of(
                NEW_RELEASE, new NewReleasePricingStrategy(),
                REGULAR, new RegularPricingStrategy(),
                OLD_EDITION, new OldEditionPricingStrategy()
        );
    }

    public BookPricingStrategy getStrategy(final BookType type) {
        return strategyMap.getOrDefault(type, (book, qty) -> {
            throw new IllegalArgumentException("No book pricing strategy for type: " + type);
        });
    }
}
