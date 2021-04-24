package ru.koshkin.persist.DAO;

import ru.koshkin.persist.Entities.ProductEntity;

import java.util.List;

public interface ProductDAOInterface {

    String truncateQuery = "truncate table product";

    ProductEntity findById(Long id);

    List<ProductEntity> findAll();

    void deleteById(Long id);

    ProductEntity saveOrUpdate(ProductEntity product);

    boolean truncate();
}
