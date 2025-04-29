package com.homework.hogwartslibrary.application.rest.order;

import com.homework.hogwartslibrary.application.rest.book.BookResponse;
import com.homework.hogwartslibrary.domain.purchase.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final PurchaseService purchaseService;

    @PostMapping
    ResponseEntity<BookResponse> placeOrder(@Valid @RequestBody final OrderRequest request) {
        purchaseService.purchaseBook(request.getCustomerId(), request.getBooks(), request.getLoyaltyDiscountBookType());
        return new ResponseEntity<>(null, CREATED);
    }
}
