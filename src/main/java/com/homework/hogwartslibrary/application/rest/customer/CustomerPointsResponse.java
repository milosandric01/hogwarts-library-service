package com.homework.hogwartslibrary.application.rest.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CustomerPointsResponse {
    private int loyaltyPoints;
}
