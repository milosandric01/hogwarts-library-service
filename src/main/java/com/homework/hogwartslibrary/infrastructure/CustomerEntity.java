package com.homework.hogwartslibrary.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jooq.generated.tables.records.CustomerRecord;

import java.util.UUID;

@RequiredArgsConstructor
public class CustomerEntity {

    private final CustomerRecord record;

    public UUID getId() {
        return UUID.fromString(record.getId());
    }

    public String getName() {
        return record.getName();
    }

    public String getLastName() {
        return record.getLastName();
    }

    public String getEmail() {
        return record.getEmail();
    }

    public int getLoyaltyPoints() {
        return record.getLoyaltyPoints();
    }
}
