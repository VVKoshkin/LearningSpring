package ru.koshkin.restServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.koshkin.pageFilters.ProductPF;
import ru.koshkin.persist.Entities.ProductEntity;
import ru.koshkin.service.ProductService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<ProductEntity> getAll() {
        return this.productService.getAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Optional<ProductEntity> getById(@PathVariable("id") Long id) {
        return this.productService.getById(id);
    }

    @GetMapping(path = "/filter", produces = "application/json")
    public Page<ProductEntity> filterUsers(ProductPF productPF) {
        return productService.findWithFilter(productPF);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity saveOrUpdate(@RequestBody ProductEntity product) {
        productService.saveOrUpdate(product);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }

}
