package com.homework.hogwartslibrary.infrastructure;

import com.homework.hogwartslibrary.domain.BookRepository;
import com.homework.hogwartslibrary.domain.service.InventoryService;
import com.homework.hogwartslibrary.domain.service.InventoryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    InventoryService orderService(final BookRepository bookRepository) {
        return new InventoryServiceImpl(bookRepository);
    }
}
