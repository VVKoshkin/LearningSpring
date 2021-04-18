package ru.koshkin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "repo")
    public Vault repo() throws ReflectiveOperationException {
        Vault repo = new ProductRepo()
                .put(new Product(1, "Чай", 15))
                .put(new Product(2, "Кофе", 25))
                .put(new Product(3, "Какао", 45))
                .put(new Product(4, "Латте", 60))
                .put(new Product(5, "Латте с карамельным сиропом", 80));
        return repo;
    }

    @Bean(name = "cart")
    public Vault cart() {
        Vault cart = new Cart();
        return cart;
    }

}
