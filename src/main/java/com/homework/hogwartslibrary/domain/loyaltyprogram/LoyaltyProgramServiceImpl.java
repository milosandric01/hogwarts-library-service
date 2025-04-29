package com.homework.hogwartslibrary.domain.loyaltyprogram;

import com.homework.hogwartslibrary.domain.CustomerRepository;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    private final CustomerRepository customerRepository;

    @Override
    public int getLoyaltyPoints(UUID id) {
        final CustomerEntity customer = customerRepository.fetch(id).orElseThrow();
        return customer.getLoyaltyPoints();
    }
}
