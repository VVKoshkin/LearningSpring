package ru.koshkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.koshkin.persist.Entities.ProductEntity;
import ru.koshkin.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) throws ReflectiveOperationException {
        this.productService = productService;
    }

    @GetMapping
    public String productListMap(@RequestParam(name = "minPrice") @Nullable BigDecimal minPrice, @RequestParam(name = "maxPrice") @Nullable BigDecimal maxPrice, Model model) {
//        model.addAttribute("prodList", productService.getAll());
        model.addAttribute("prodList", productService.findBetweenPrices(minPrice, maxPrice));
        model.addAttribute("minPrice");
        model.addAttribute("maxPrice");
        return ("product");
    }

    @GetMapping("/{id}")
    public String productFormMap(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product_form";
    }

    @PostMapping
    public String submitForm(@Valid ProductEntity product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product_form";
        }
        productService.saveOrUpdate(product);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String productNewMap(Model model) throws ReflectiveOperationException {
        ProductEntity entity = new ProductEntity();
        model.addAttribute("product", entity);
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String deleteProductMap(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/product";
    }
}