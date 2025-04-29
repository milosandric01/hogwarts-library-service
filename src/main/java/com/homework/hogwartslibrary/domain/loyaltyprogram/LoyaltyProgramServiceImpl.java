package com.homework.hogwartslibrary.domain.loyaltyprogram;

import com.homework.hogwartslibrary.domain.CustomerRepository;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    private final CustomerRepository customerRepository;

    @Override
    public int getLoyaltyPoints(final UUID id) {
        final CustomerEntity customer = customerRepository.fetch(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + id + " not found"));

        return customer.getLoyaltyPoints();
    }
}
