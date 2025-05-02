package com.homework.hogwartslibrary.application.rest.order;

import com.homework.hogwartslibrary.domain.purchase.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final PurchaseService purchaseService;

    @Operation(
            summary = "Place order",
            description = "Place order for single customer for one or more books",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order place successful"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping
    ResponseEntity<Void> placeOrder(@Valid @RequestBody final OrderRequest request) {
        purchaseService.purchaseBook(request.getCustomerId(), request.getBooks(), request.getLoyaltyDiscountBookType());
        return ResponseEntity.ok().build();
    }
}
