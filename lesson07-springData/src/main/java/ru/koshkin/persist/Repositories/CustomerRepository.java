package ru.koshkin.persist.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.koshkin.persist.Entities.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
