package ru.koshkin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.ProductEntity;
import ru.koshkin.persist.Repositories.ProductRepository;
import ru.koshkin.service.interfaces.StandartService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements StandartService<ProductEntity> {

    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity getById(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @Override
    @Transactional
    public void saveOrUpdate(ProductEntity entity) {
        productRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(ProductEntity entity) {
        productRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductEntity> findBetweenPrices(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null)
            minPrice = BigDecimal.valueOf(0);
        if (maxPrice == null)
            maxPrice = BigDecimal.valueOf(Long.MAX_VALUE);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

}
