package com.homework.hogwartslibrary.domain.purchase;

import com.homework.hogwartslibrary.domain.BookType;

import java.util.List;
import java.util.UUID;

public interface PurchaseService {

    void purchaseBook(UUID customerId, List<UUID> books, BookType loyaltyDiscountBookType);
}
