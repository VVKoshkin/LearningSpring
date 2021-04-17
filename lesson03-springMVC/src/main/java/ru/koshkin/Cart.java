package ru.koshkin;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Vault {

    private ArrayList<Product> products = new ArrayList<>();

    @Override
    public String getList() {
        if (products.size() == 0) {
            return "Ваша корзина пуста!";
        } else {
            StringBuilder sb = new StringBuilder("Ваша корзина:");
            sb.append(System.lineSeparator());
            products.forEach(elem -> sb.append(String.format("%d - %s - %d руб", elem.getId(), elem.getName(), elem.getPrice())).append(System.lineSeparator()));
            return sb.toString();
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("Введите номер продукта и нажмите Enter чтоб положить продукт в корзину" +
                System.lineSeparator() + "Введите номер продукта со знаком \"-\" чтобы удалить его из корзины, если он там есть" +
                System.lineSeparator() + "Введите 0 чтобы закончить");
    }

    @Override
    public List<Storable> getAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Storable getById(Integer id) {
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
