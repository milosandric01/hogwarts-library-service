package com.homework.hogwartslibrary.application.rest.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {
    @NotNull(message = "Customer id must not be null")
    private UUID customerId;

    @NotEmpty(message = "Book ids must not be empty")
    private List<UUID> books;

    @NotNull(message = "Choice to use loyalty points must not be null")
    private Boolean useLoyaltyPoints;
}
