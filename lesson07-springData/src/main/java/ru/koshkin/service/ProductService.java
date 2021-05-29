package ru.koshkin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.koshkin.persist.Entities.ProductEntity;
import ru.koshkin.persist.Repositories.ProductRepository;
import ru.koshkin.persist.Specifications.ProductSpecification;
import ru.koshkin.pageFilters.ProductPF;
import ru.koshkin.service.interfaces.StandartService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements StandartService<ProductEntity> {

    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<ProductEntity> getById(Long id) {
        return productRepository.findById(id);
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

    public Page<ProductEntity> findWithFilter(ProductPF productPF) {
        Specification<ProductEntity> specification = Specification.where(null);
        if (productPF.getMinPrice() != null) {
            specification = specification.and(ProductSpecification.productMinPrice(productPF.getMinPrice()));
        }
        if (productPF.getMaxPrice() != null) {
            specification = specification.and(ProductSpecification.productMaxPrice(productPF.getMaxPrice()));
        }
        if (productPF.getProductName() != null && !productPF.getProductName().isBlank()) {
            specification = specification.and(ProductSpecification.productNameLike("%" + productPF.getProductName() + "%"));
        }
        if (productPF.getSortBy() != null) {
//            specification = specification.and(ProductSpecification.order(productPF.getSortBy()));
        }
        PageRequest request = PageRequest.of(
                productPF.getPage() == null ? 0 : productPF.getPage() - 1,
                productPF.getSize() == null ? 5 : productPF.getSize(),
                Sort.by(productPF.getSortDirection(), productPF.getSortBy() == null ? ProductPF.DEFAULT_SORT : productPF.getSortBy())
        );
        return productRepository.findAll(specification, request);
    }
}
