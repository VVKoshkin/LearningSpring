package ru.koshkin.persist.DAO;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.Entities.CustomerEntity;
import ru.koshkin.persist.Entities.LineItemEntity;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.ProductEntity;

import java.util.List;

public class LineItemDAO extends DAO {

    private final static Logger logger = LoggerFactory.getLogger(LineItemDAO.class);

    @Override
    public Persistable getById(Long id) {
        return executeSelect(session -> session.get(LineItemEntity.class, id));
    }

    @Override
    public List<Persistable> findAll() {
        return executeSelect(session -> session.createCriteria(LineItemEntity.class).list());
    }

    @Override
    public void saveOrUpdate(Persistable object) {
        if (object instanceof LineItemEntity)
            executeUpdate(session -> {
                if (object.getId() == null) {
                    session.save(object);
                } else {
                    session.update(object);
                }
            });
    }

    @Override
    public void delete(Persistable object) {
        if (object instanceof LineItemEntity)
            executeUpdate(session -> session.delete(object));
    }

    public LineItemEntity findByProductAndCustomer(ProductEntity productEntity, CustomerEntity customerEntity) {
        return executeSelect(session -> (LineItemEntity) session.createCriteria(LineItemEntity.class)
                .createAlias("customerEntity", "customerEntity")
                .createAlias("productEntity", "productEntity")
                .add(Restrictions.eq("customerEntity.id", customerEntity.getId()))
                .add(Restrictions.eq("productEntity.id", productEntity.getId()))
                .uniqueResult());
    }
}
