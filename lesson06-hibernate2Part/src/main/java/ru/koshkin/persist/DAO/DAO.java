package ru.koshkin.persist.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.Entities.Persistable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DAO {

    private final static Logger logger = LoggerFactory.getLogger(DAO.class);

    protected SessionFactory factory;

    public DAO() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public abstract Persistable getById(Long id);

    public abstract List<Persistable> findAll();

    public abstract void saveOrUpdate(Persistable object);

    public abstract void delete(Persistable object);

    protected <R> R executeSelect(Function<Session, R> function) {
        Session session = null;
        try {
            session = factory.openSession();
            R result = function.apply(session);
            return result;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    protected void executeUpdate(Consumer<Session> consumer) {
        Session session = null;
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            consumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }

    }
}
