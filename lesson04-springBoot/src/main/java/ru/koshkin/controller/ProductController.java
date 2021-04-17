package ru.koshkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.koshkin.Product;
import ru.koshkin.ProductRepo;
import ru.koshkin.Storable;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepo repo;

    @Autowired
    public ProductController(ProductRepo repo) throws ReflectiveOperationException {
        this.repo = repo;
        logger.info(this.repo.getList());
    }

    @GetMapping
    public String productListMap(Model model) {
        logger.info(repo.getList());
        model.addAttribute("prodList", repo.getAll());
        return ("product");
    }

    @GetMapping("/{id}")
    public String productFormMap(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", repo.getById(id));
        return "product_form";
    }

    @PostMapping
    public String submitForm(@Valid Product product, BindingResult bindingResult) throws ReflectiveOperationException {
        if (bindingResult.hasErrors()) {
            return "product_form";
        }
        repo.put(product);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String productNewMap(Model model) throws ReflectiveOperationException {
        Storable product = new Product(null, null, null);
        model.addAttribute("product", product);
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String deleteProductMap(@PathVariable("id") int id) {
        repo.delete(id);
        return "redirect:/product";
    }
}
