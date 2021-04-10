package ru.koshkin;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Client {

    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
//            usualRealization(sc); // обычная реализация без бинов - не часть задания, пришлось сделать, чтобы понять, чего вообще должно получиться в итоге
            springRealization(sc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // реализация без спринга
    private static void usualRealization(Scanner sc) throws ReflectiveOperationException {
        Vault repo = new ProductRepo();
        repo.put(new Product(1, "Чай", 15));
        repo.put(new Product(2, "Кофе", 25));
        repo.put(new Product(3, "Какао", 45));
        Vault cart = new Cart();
        startShopping(repo, cart, sc);
    }


    // реализация со спрингом
    private static void springRealization(Scanner sc) throws ReflectiveOperationException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Vault repo = context.getBean("repo", ProductRepo.class);
        Vault cart = context.getBean("cart", Cart.class);
        startShopping(repo, cart, sc);
    }

    private static void startShopping(Vault repo, Vault cart, Scanner sc) throws ReflectiveOperationException {
        System.out.println(repo.getList());
        System.out.println(cart.getList());
        Integer id = sc.nextInt();
        while (id != 0) {
            if (id > 0 && repo.getById(id) != null) {
                cart.put(repo.getById(id));
            } else if (cart.getById(Math.abs(id)) != null) {
                cart.delete(Math.abs(id));
            }
            System.out.println(cart.getList());
            id = sc.nextInt();
        }
    }
}
