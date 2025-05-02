package com.homework.hogwartslibrary.infrastructure.relational;

import com.homework.hogwartslibrary.domain.Order;
import com.homework.hogwartslibrary.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.jooq.generated.tables.OrderItem.ORDER_ITEM;
import static org.jooq.generated.tables.Orders.ORDERS;

@Repository
@RequiredArgsConstructor
public class RelationalOrderRepository implements OrderRepository {

    private final DSLContext dslContext;


    @Override
    public void save(final Order order) {
        final String orderId = UUID.randomUUID().toString();

        dslContext.insertInto(ORDERS, ORDERS.ID, ORDERS.CUSTOMER_ID, ORDERS.TOTAL_PRICE, ORDERS.ORDER_DATE)
                .values(orderId, order.getCustomerId().toString(), order.getTotalPrice(), LocalDateTime.now())
                .execute();

        dslContext.batch(order.getOrderItems().stream().map(item ->
                dslContext.insertInto(ORDER_ITEM, ORDER_ITEM.ID, ORDER_ITEM.ORDER_ID, ORDER_ITEM.BOOK_ID, ORDER_ITEM.UNIT_PRICE, ORDER_ITEM.DISCOUNTED_PRICE)
                        .values(UUID.randomUUID().toString(), orderId, item.getBookId().toString(), item.getUnitPrice(), item.getDiscountedPrice())
        ).toList()).execute();
    }
}
