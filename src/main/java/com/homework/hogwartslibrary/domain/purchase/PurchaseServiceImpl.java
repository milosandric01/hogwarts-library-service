package com.homework.hogwartslibrary.domain.purchase;

import com.homework.hogwartslibrary.domain.*;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final BookRepository bookRepository;

    private final BookPricingStrategyFactory bookPricingStrategyFactory;

    @Override
    public void purchaseBook(final UUID customerId, final List<UUID> bookIds, final boolean useLoyaltyPoints) {

        final CustomerEntity customer = customerRepository.fetch(customerId).orElseThrow();

        List<OrderItem> orderItems = bookIds.stream().map(id -> createOrderItem(id, bookIds.size())).toList();

        new Order(customer.getId(), orderItems);
    }

    public OrderItem createOrderItem(final UUID bookId, int size) {
        final BookEntity book = bookRepository.fetch(bookId).orElseThrow();
        final BookPricingStrategy strategy = bookPricingStrategyFactory.getStrategy(book.getType());

        return new OrderItem(book.getId(), book.getBasePrice(), calculateDiscountedPrice(book, size));
    }

    private BigDecimal calculateDiscountedPrice(BookEntity book, int totalBookCount) {
        BigDecimal price = book.getBasePrice();

        switch (book.getType()) {
            case NEW_RELEASE -> {
                return price; // 100%
            }
            case REGULAR -> {
                if (totalBookCount >= 3) {
                    return price.multiply(BigDecimal.valueOf(0.9)); // 10% off
                }

                return price;
            }
            case OLD_EDITION -> {
                BigDecimal discounted = price.multiply(BigDecimal.valueOf(0.8)); // 20% off
                if (totalBookCount >= 3) {
                    discounted = discounted.multiply(BigDecimal.valueOf(0.95)); // additional 5% off
                }
                return discounted;
            }
            default -> throw new IllegalStateException("Unsupported book type: " + book.getType());
        }
    }
}
