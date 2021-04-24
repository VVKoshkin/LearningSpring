package ru.koshkin;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("repo")
public class ProductRepo implements Vault {

    List<Product> products = new ArrayList<>();

    @Override
    public String getList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Доступные товары для покупки:").append(System.lineSeparator());
        products.forEach(elem -> sb.append(String.format("%d - %s - %d", elem.getId(), elem.getName(), elem.getPrice())).append(System.lineSeparator()));
        return sb.toString();
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
