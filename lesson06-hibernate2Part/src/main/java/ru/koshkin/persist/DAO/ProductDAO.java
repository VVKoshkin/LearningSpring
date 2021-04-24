package ru.koshkin.persist.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.ProductEntity;

import java.util.List;

@Component
public class ProductDAO extends DAO {

    private final static Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    @Override
    public Persistable getById(Long id) {
        return executeSelect(session -> (ProductEntity) session.get(ProductEntity.class, id));
    }

    @Override
    public List<Persistable> findAll() {
        return executeSelect(session -> session.createCriteria(ProductEntity.class).list());
    }

    @Override
    public void saveOrUpdate(Persistable object) {
        if (object instanceof ProductEntity) {
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
        if (object instanceof ProductEntity) {
            executeUpdate(session -> {
                session.delete(object);
            });
        }
    }
}
