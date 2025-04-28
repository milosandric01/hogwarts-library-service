package com.homework.hogwartslibrary.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Order {
    private UUID id;
    private UUID customerId;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;
    private List<OrderItem> orderItems;
}
