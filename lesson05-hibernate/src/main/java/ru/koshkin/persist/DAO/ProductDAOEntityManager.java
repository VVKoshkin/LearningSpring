package ru.koshkin.persist.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.Entities.ProductEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductDAOEntityManager implements ProductDAOInterface {

    private final static Logger logger = LoggerFactory.getLogger(ProductDAOEntityManager.class);

    private EntityManager manager;

    public ProductDAOEntityManager(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public ProductEntity findById(Long id) {
        return manager.find(ProductEntity.class, id);
    }

    @Override
    public List<ProductEntity> findAll() {
        return manager.createQuery("select p from ProductEntity p", ProductEntity.class).getResultList();
    }

    @Override
    public void deleteById(Long id) {
        ProductEntity productEntity = manager.find(ProductEntity.class, id);
        if (productEntity == null) {
            return;
        }
        manager.getTransaction().begin();
        try {
            manager.remove(productEntity);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
    }

    @Override
    public boolean truncate() {
        try {
            manager.getTransaction().begin();
            manager.createNativeQuery(truncateQuery).executeUpdate();
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductEntity saveOrUpdate(ProductEntity product) {
        manager.getTransaction().begin();
        try {
            if (product.getId() != null && manager.find(ProductEntity.class, product.getId()) != null) {
                // UPDATE
                manager.merge(product);
            } else {
                // INSERT NEW
                manager.persist(product);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            manager.getTransaction().rollback();
        }
        return product;
    }

}
