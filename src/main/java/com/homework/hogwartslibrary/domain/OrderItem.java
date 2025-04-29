package com.homework.hogwartslibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderItem {
    private UUID bookId;
    private BigDecimal unitPrice;
    private BigDecimal discountedPrice;
}
