package com.homework.hogwartslibrary.infrastructure.relational;

import com.homework.hogwartslibrary.domain.CustomerRepository;
import com.homework.hogwartslibrary.infrastructure.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static org.jooq.generated.Tables.CUSTOMER;

@Repository
@RequiredArgsConstructor
public class RelationalCustomerRepository implements CustomerRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<CustomerEntity> fetch(final UUID id) {
        return Optional.ofNullable(
                dslContext.selectFrom(CUSTOMER)
                        .where(CUSTOMER.ID.eq(id.toString()))
                        .fetchOne()
        ).map(CustomerEntity::new);
    }

    @Override
    public void updateLoyaltyPoints(final UUID id, final int newPoints) {
        dslContext.update(CUSTOMER)
                .set(CUSTOMER.LOYALTY_POINTS, newPoints)
                .where(CUSTOMER.ID.eq(id.toString()))
                .execute();
    }
}
