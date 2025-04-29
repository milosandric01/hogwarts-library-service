package com.homework.hogwartslibrary.infrastructure;

import com.homework.hogwartslibrary.domain.BookRepository;
import com.homework.hogwartslibrary.domain.CustomerRepository;
import com.homework.hogwartslibrary.domain.inventory.InventoryService;
import com.homework.hogwartslibrary.domain.inventory.InventoryServiceImpl;
import com.homework.hogwartslibrary.domain.loyaltyprogram.LoyaltyProgramService;
import com.homework.hogwartslibrary.domain.loyaltyprogram.LoyaltyProgramServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    InventoryService orderService(final BookRepository bookRepository) {
        return new InventoryServiceImpl(bookRepository);
    }

    @Bean
    LoyaltyProgramService loyaltyProgramService(final CustomerRepository customerRepository) {
        return new LoyaltyProgramServiceImpl(customerRepository);
    }
}
