package ru.koshkin.persist.DAO;

import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.CustomerEntity;
import ru.koshkin.persist.Entities.ProductEntity;

import java.util.List;
import java.util.Set;

@Component
public class CustomerDAO extends DAO {

    private final static Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    @Override
    public Persistable getById(Long id) {
        return executeSelect(session -> (CustomerEntity) session.createCriteria(CustomerEntity.class)
                .createAlias("lineItems", "lineItems", CriteriaSpecification.LEFT_JOIN)
                .add(Restrictions.eq("id", id))
                .addOrder(Order.asc("id"))
                .uniqueResult());
    }

    @Override
    public List<Persistable> findAll() {
        return executeSelect(session -> session.createCriteria(CustomerEntity.class)
                .addOrder(Order.asc("id"))
                .list());
    }

    @Override
    public void saveOrUpdate(Persistable object) {
        if (object instanceof CustomerEntity) {
            executeUpdate(session -> {
                if (object.getId() != null) {
                    session.save(object);
                } else {
                    session.persist(object);
                }
            });
        }
    }

    @Override
    public void delete(Persistable object) {
        if (object instanceof CustomerEntity) {
            executeUpdate(session -> {
                session.delete(object);
            });
        }
    }
}
