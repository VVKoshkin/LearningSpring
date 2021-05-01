package ru.koshkin.persist.Specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.koshkin.persist.Entities.ProductEntity;

import java.math.BigDecimal;

public final class ProductSpecification {

    public static Specification<ProductEntity> productMinPrice(BigDecimal minPrice) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), minPrice));
    }

    public static Specification<ProductEntity> productMaxPrice(BigDecimal maxPrice) {
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(root.get("price"), maxPrice));
    }

    public static Specification<ProductEntity> productNameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), name);
    }
}
