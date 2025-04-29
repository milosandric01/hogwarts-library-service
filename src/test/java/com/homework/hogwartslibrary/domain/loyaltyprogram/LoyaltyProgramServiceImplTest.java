package com.homework.hogwartslibrary.domain.loyaltyprogram;

import com.homework.hogwartslibrary.domain.CustomerRepository;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoyaltyProgramServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private LoyaltyProgramServiceImpl loyaltyProgramService;

    @Test
    void getLoyaltyPoints_shouldThrowIfCustomerNotFound() {
        final UUID customerId = UUID.randomUUID();
        when(customerRepository.fetch(customerId)).thenReturn(Optional.empty());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loyaltyProgramService.getLoyaltyPoints(customerId));

        assertEquals("Customer with ID " + customerId + " not found", exception.getMessage());
    }

    @Test
    void getLoyaltyPoints_shouldReturnCustomerPoints() {
        final UUID customerId = UUID.randomUUID();
        final CustomerEntity customer = mock(CustomerEntity.class);

        when(customer.getLoyaltyPoints()).thenReturn(15);
        when(customerRepository.fetch(customerId)).thenReturn(Optional.of(customer));

        final int points = loyaltyProgramService.getLoyaltyPoints(customerId);

        verify(customerRepository).fetch(customerId);

        assertEquals(15, points);
    }
}