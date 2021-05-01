package ru.koshkin.persist.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.koshkin.persist.Entities.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    List<ProductEntity> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

}
