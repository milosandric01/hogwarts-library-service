package com.homework.hogwartslibrary.domain;

import com.homework.hogwartslibrary.infrastructure.CustomerEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<CustomerEntity> fetch(UUID id);
}
