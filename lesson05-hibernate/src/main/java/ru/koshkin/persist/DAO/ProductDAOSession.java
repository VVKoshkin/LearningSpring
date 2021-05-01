package ru.koshkin.persist.DAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.Entities.ProductEntity;

import java.util.List;

@SuppressWarnings("deprecated")
public class ProductDAOSession implements ProductDAOInterface {
    private final static Logger logger = LoggerFactory.getLogger(ProductDAOSession.class);
    Session session;

    public ProductDAOSession(Session session) {
        this.session = session;
    }

    @Override
    public ProductEntity findById(Long id) {
        return session.get(ProductEntity.class, id);
    }

    @Override
    public List<ProductEntity> findAll() {

        Criteria criteria = session.createCriteria(ProductEntity.class);
        return criteria.list();

    }

    @Override
    public void deleteById(Long id) {
        ProductEntity productEntity = session.get(ProductEntity.class, id);
        if (productEntity == null) {
            return;
        }
        session.beginTransaction();
        try {
            session.delete(productEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }


    @Override
    public boolean truncate() {
        try {
            session.beginTransaction();
            session.createSQLQuery(truncateQuery).executeUpdate();
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ProductEntity saveOrUpdate(ProductEntity product) {
        session.beginTransaction();
        try {
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return product;
    }
}
