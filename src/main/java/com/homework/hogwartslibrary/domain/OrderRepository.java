package com.homework.hogwartslibrary.domain;

import java.util.UUID;

public interface OrderRepository {

    void save(Order order, UUID customerId);
}
