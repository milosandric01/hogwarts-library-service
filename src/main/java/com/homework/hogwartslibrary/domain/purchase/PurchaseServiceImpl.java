package com.homework.hogwartslibrary.domain.purchase;

import com.homework.hogwartslibrary.domain.*;
import com.homework.hogwartslibrary.domain.purchase.strategy.BookPricingStrategy;
import com.homework.hogwartslibrary.domain.purchase.strategy.BookPricingStrategyFactory;
import com.homework.hogwartslibrary.infrastructure.BookEntity;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.homework.hogwartslibrary.domain.BookType.NEW_RELEASE;

@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final BookRepository bookRepository;

    private final BookPricingStrategyFactory bookPricingStrategyFactory;

    @Override
    public void purchaseBook(final UUID customerId, final List<UUID> bookIds, final BookType loyaltyDiscountBookType) {

        final CustomerEntity customer = customerRepository.fetch(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found"));

        final List<OrderItem> orderItems = bookIds.stream().map(id -> createOrderItem(id, bookIds.size(), customer.getLoyaltyPoints(), loyaltyDiscountBookType)).toList();

        final BigDecimal totalPrice = orderItems.stream().map(OrderItem::getDiscountedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        updateLoyaltyPoints(orderItems, customer, bookIds.size());

        final Order order = Order.builder()
                .customerId(customer.getId())
                .totalPrice(totalPrice)
                .orderItems(orderItems)
                .build();

        orderRepository.save(order);
    }

    public OrderItem createOrderItem(final UUID bookId, final int size, final int loyaltyPoints, final BookType bookTypeToApplyLoyaltyDiscount) {
        final BookEntity book = bookRepository.fetch(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));
        final BookPricingStrategy strategy = bookPricingStrategyFactory.getStrategy(book.getType());

        final boolean loyaltyDiscountApplicable = isLoyaltyDiscountApplicable(loyaltyPoints, book.getType(), bookTypeToApplyLoyaltyDiscount);
        final BigDecimal price = loyaltyDiscountApplicable ? BigDecimal.ZERO : strategy.calculatePrice(book, size);
        return new OrderItem(book.getId(), book.getBasePrice(), price);
    }

    private boolean isLoyaltyDiscountApplicable(final int loyaltyPoints, final BookType currentType, final BookType typeToApplyLoyaltyDiscount) {
        if (NEW_RELEASE == typeToApplyLoyaltyDiscount) {
            throw new IllegalArgumentException("This book type does not support loyalty discount");
        }

        return loyaltyPoints > 10 && typeToApplyLoyaltyDiscount == currentType;
    }

    private void updateLoyaltyPoints(final List<OrderItem> orderItems, final CustomerEntity customer, final int earnedPoints) {
        final boolean isLoyaltyDiscountApplied = orderItems.stream().anyMatch(item -> item.getDiscountedPrice().compareTo(BigDecimal.ZERO) == 0);
        final int newPoints = isLoyaltyDiscountApplied ? 0 : customer.getLoyaltyPoints() + earnedPoints;
        customerRepository.updateLoyaltyPoints(customer.getId(), newPoints);
    }
}
