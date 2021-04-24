package ru.koshkin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.DAO.ProductDAOEntityManager;
import ru.koshkin.persist.DAO.ProductDAOInterface;
import ru.koshkin.persist.DAO.ProductDAOSession;
import ru.koshkin.persist.Entities.ProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MainX {

    private final static Logger logger = LoggerFactory.getLogger(MainX.class);

    public static void main(String[] args) {
        sessionTesting();
        emTesting();
    }

    private static void sessionTesting() {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        Session session = null;
        try {
            session = factory.openSession();
            ProductDAOSession daoSession = new ProductDAOSession(session);
            doSomething(daoSession);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    private static void emTesting() {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            ProductDAOEntityManager daoEntityManager = new ProductDAOEntityManager(em);
            doSomething(daoEntityManager);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private static void doSomething(ProductDAOInterface dao) {
        // кладём новых продуктов
        ProductEntity[] productEntities = {
                new ProductEntity("Чай", 15),
                new ProductEntity("Кофе", 25),
                new ProductEntity("Какао", 35),
                new ProductEntity("Каппучино", 55),
                new ProductEntity("Латте с сиропом", 85),
        };
        for (ProductEntity entity : productEntities) {
            dao.saveOrUpdate(entity);
        }
        // выбираем по идентификатору
        ProductEntity someProduct = dao.findById(productEntities[2].getId()); // ищем какао
        if (someProduct != null) {
            System.out.println(someProduct.toString());
        }
        // смотрим все
        List<ProductEntity> productEntityList = dao.findAll();
        for (ProductEntity entity : productEntityList) {
            System.out.println(entity.toString());
        }
        // удаляем что-то по ID
        dao.deleteById(productEntities[4].getId()); // удаляем латте
        // опять смотрим всё
        productEntityList = dao.findAll();
        for (ProductEntity entity : productEntityList) {
            System.out.println(entity.toString());
        }
        // убиваем таблицу
        dao.truncate();
    }

}
