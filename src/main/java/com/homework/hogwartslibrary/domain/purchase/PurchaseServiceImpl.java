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

        final BigDecimal totalPrice = orderItems.stream().map(OrderItem::getDiscountedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        final Order order = Order.builder()
                .customerId(customer.getId())
                .totalPrice(totalPrice)
                .orderItems(orderItems)
                .build();

        orderRepository.save(order);
    }

    public OrderItem createOrderItem(final UUID bookId, int size) {
        final BookEntity book = bookRepository.fetch(bookId).orElseThrow();
        final BookPricingStrategy strategy = bookPricingStrategyFactory.getStrategy(book.getType());

        return new OrderItem(book.getId(), book.getBasePrice(), strategy.calculatePrice(book, size));
    }
}
