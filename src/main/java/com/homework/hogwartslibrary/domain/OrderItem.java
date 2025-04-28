package com.homework.hogwartslibrary.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItem {
    private UUID bookId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountedPrice;
}
