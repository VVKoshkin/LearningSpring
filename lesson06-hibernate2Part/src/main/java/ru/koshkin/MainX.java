package ru.koshkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.koshkin.persist.DAO.DAO;
import ru.koshkin.persist.DAO.CustomerDAO;
import ru.koshkin.persist.DAO.LineItemDAO;
import ru.koshkin.persist.DAO.ProductDAO;
import ru.koshkin.persist.Entities.LineItemEntity;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.CustomerEntity;
import ru.koshkin.persist.Entities.ProductEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class MainX {

    private final static Logger logger = LoggerFactory.getLogger(MainX.class);

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        try (Scanner sc = new Scanner(System.in)) {
//            CustomerDAO customerDAO = context.getBean("customerDAO", CustomerDAO.class);
            CustomerDAO customerDAO = new CustomerDAO();
            List<Persistable> userEntityList = customerDAO.findAll();
            StringBuilder builder = new StringBuilder();
            for (Persistable p : userEntityList) {
                CustomerEntity customerEntity = (CustomerEntity) p;
                builder.append(String.format("%d: %s", customerEntity.getId(), customerEntity.getName()));
                builder.append(System.lineSeparator());
            }
            System.out.println("Под каким пользователем нужно логиниться?\n");
            System.out.println(builder);
            Long userId = sc.nextLong();
            while (true) {
                CustomerEntity customerEntity = (CustomerEntity) customerDAO.getById(userId);
                if (customerEntity == null) {
                    System.out.println("Нет такого пользователя");
                    break;
                }
                System.out.println("Корзина ".concat(customerEntity.getName()));
                List<LineItemEntity> lineItems = customerEntity.getLineItems();
                for (LineItemEntity item : lineItems) {
                    ProductEntity productEntity = item.getProductEntity();
                    BigDecimal price = item.getProductPrice();
                    System.out.println(String.format("%s\t%s", productEntity.getName(), price.toString()));
                }
//            ProductDAO productDAO = context.getBean("productDAO", ProductDAO.class);
                ProductDAO productDAO = new ProductDAO();
                LineItemDAO lineItemDAO = new LineItemDAO();
                System.out.println("Доступные для покупки товары:");
                List<Persistable> list = productDAO.findAll();
                for (Persistable o : list) {
                    ProductEntity entity = (ProductEntity) o;
                    System.out.println(String.format("%d\t%s\t%s", entity.getId(), entity.getName(), entity.getPrice().toString()));
                }
                System.out.println("Введите идентификатор чтобы добавить товар в корзину или идентификатор со знаком \"-\" чтобы его удалить из корзины\n" +
                        "Чтобы закончить, введите 0");
                Long productId = sc.nextLong();
                if (productId == 0) {
                    break;
                }
                ProductEntity newProduct = (ProductEntity) productDAO.getById(Math.abs(productId));
                if (newProduct == null) {
                    System.out.println("Такого товара нет в наличии");
                    continue;
                }
                if (productId > 0) {
                    // больше нуля - кладём товар в корзину
                    LineItemEntity entity = new LineItemEntity();
                    entity.setCustomerEntity(customerEntity);
                    entity.setProductEntity(newProduct);
                    entity.setProductPrice(newProduct.getPrice());
                    lineItemDAO.saveOrUpdate(entity);
                } else if (productId < 0) {
                    // меньше нуля - удаляем, если он там есть
//                    LineItemDAO lineItemDAO = context.getBean("lineItemDAO", LineItemDAO.class);
                    LineItemEntity entity = lineItemDAO.findByProductAndCustomer(newProduct, customerEntity);
                    if (entity == null) {
                        System.out.println("Такого товара нет в корзине этого покупателя");
                        continue;
                    }
                    lineItemDAO.delete(entity);
                }
            }
        }
    }
}
