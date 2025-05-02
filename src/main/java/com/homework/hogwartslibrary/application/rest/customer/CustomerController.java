package com.homework.hogwartslibrary.application.rest.customer;

import com.homework.hogwartslibrary.domain.loyaltyprogram.LoyaltyProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final LoyaltyProgramService loyaltyProgramService;

    @Operation(
            summary = "Get customer available loyalty points",
            description = "Fetches loyalty points for customer by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Loyalty points fetch successfully",
                            content = @Content(schema = @Schema(implementation = CustomerPointsResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}/loyalty-points")
    public ResponseEntity<CustomerPointsResponse> getLoyaltyPoints(@PathVariable final UUID id) {

        final int loyaltyPoints = loyaltyProgramService.getLoyaltyPoints(id);
        return ResponseEntity.ok(new CustomerPointsResponse(loyaltyPoints));
    }
}
