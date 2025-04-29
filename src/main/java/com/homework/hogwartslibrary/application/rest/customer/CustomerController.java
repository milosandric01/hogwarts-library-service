package com.homework.hogwartslibrary.application.rest.customer;

import com.homework.hogwartslibrary.domain.loyaltyprogram.LoyaltyProgramService;
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

    @GetMapping("/{id}/loyalty-points")
    public ResponseEntity<CustomerPointsResponse> getLoyaltyPoints(@PathVariable final UUID id) {

        final int loyaltyPoints = loyaltyProgramService.getLoyaltyPoints(id);
        return ResponseEntity.ok(new CustomerPointsResponse(loyaltyPoints));
    }
}
