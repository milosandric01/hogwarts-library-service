package com.homework.hogwartslibrary.domain.purchase;

import java.util.List;
import java.util.UUID;

public interface PurchaseService {

    void purchaseBook(UUID customerId, List<UUID> books, boolean useLoyaltyPoints);
}
