package ru.koshkin.persist.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.koshkin.persist.Entities.LineItemEntity;

public interface LineItemRepository extends JpaRepository<LineItemEntity, Long> {
}
