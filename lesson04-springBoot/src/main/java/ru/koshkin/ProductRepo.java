package ru.koshkin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.koshkin.controller.ProductController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component("repo")
public class ProductRepo implements Vault {

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final List<Product> products = new ArrayList<>();

    @Override
    public String getList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Доступные товары для покупки:").append(System.lineSeparator());
        products.forEach(elem -> sb.append(String.format("%d - %s - %d", elem.getId(), elem.getName(), elem.getPrice())).append(System.lineSeparator()));
        return sb.toString();
    }

    @PostConstruct
    public void init() throws ReflectiveOperationException {
        put(new Product(null, "Чай", 15))
                .put(new Product(null, "Кофе", 25))
                .put(new Product(null, "Какао", 45))
                .put(new Product(null, "Латте", 60))
                .put(new Product(null, "Латте с карамельным сиропом", 80));
    }

    @Override
    public List<Storable> getAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Product getById(Integer id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public Vault put(Storable element) throws ReflectiveOperationException {
        if (element instanceof Product) {
            for (Product product : products) {
                if (product.getId() == element.getId()) {
//                    если нашли продукт с таким же идентификатором - это редактирование
                    product.setName(element.getName());
                    product.setPrice(element.getPrice());
                    return this;
                }
            }
//            иначе - добавление нового
            products.add((Product) element);
            return this;
        } else {
            throw new ReflectiveOperationException(Product.class.getName() + " class expected, " + element.getClass().getName() + " got");
        }
    }

    @Override
    public Vault delete(Integer id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                break;
            }
        }
        return this;
    }
}
