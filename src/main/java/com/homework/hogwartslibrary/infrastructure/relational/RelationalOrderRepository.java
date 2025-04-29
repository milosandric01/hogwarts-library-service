package com.homework.hogwartslibrary.infrastructure.relational;

import com.homework.hogwartslibrary.domain.Order;
import com.homework.hogwartslibrary.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.jooq.generated.tables.Book.BOOK;
import static org.jooq.generated.tables.Orders.ORDERS;
import static org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor.ORDER;

@Repository
@RequiredArgsConstructor
public class RelationalOrderRepository implements OrderRepository {

    private final DSLContext dslContext;


    @Override
    @Transactional
    public void save(final Order order, final UUID customerId) {
        final String id = UUID.randomUUID().toString();

        dslContext.insertInto(ORDERS, ORDERS.ID, ORDERS.CUSTOMER_ID, ORDERS.TOTAL_PRICE, ORDERS.ORDER_DATE)
                .values(id, customerId.toString(), order.getTotalPrice(), LocalDateTime.now())
                .execute();
    }
}
