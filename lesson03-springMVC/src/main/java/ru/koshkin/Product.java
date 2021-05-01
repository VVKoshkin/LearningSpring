package ru.koshkin;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.validation.constraints.*;

//@Component("product")
public class Product implements Storable {
    private Integer id;
    @Size(min = 3, message = "Имя продукта не менее 3 символов")
    @NotBlank(message = "Имя продукта не может быть пустое")
    private String name;
    @PositiveOrZero(message = "Цена на продукт не может быть отрицательной")
//    @Pattern(regexp = "\\d+(\\.\\d+)?", message = "Неверный формат цены")
    private Integer price;
    private static int autoIncrement = 1;

    @Autowired
    public Product(Integer id, String name, Integer price) {
        if (id == null) {
            id = autoIncrement++;
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @PostConstruct
    public void init() {
        // если получили идентификатор больше текущего автоинкремента
        // повышаем автоинкремент до этого идентификатора
        // чтоб потом не было дублирований
        if (id > autoIncrement) {
            autoIncrement = id + 1;
        }
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
