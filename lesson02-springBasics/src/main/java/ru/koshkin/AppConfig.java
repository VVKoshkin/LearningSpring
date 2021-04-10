package ru.koshkin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean(name = "repo")
    public Vault repo() throws ReflectiveOperationException {
        Vault repo = new ProductRepo();
        repo.put(new Product(1, "Чай", 15));
        repo.put(new Product(2, "Кофе", 25));
        repo.put(new Product(3, "Какао", 45));
        return repo;
    }

    @Bean(name = "cart")
    public Vault cart() {
        Vault cart = new Cart();
        return cart;
    }

}
