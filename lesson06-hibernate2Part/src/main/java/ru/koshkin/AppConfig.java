package ru.koshkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.koshkin.persist.DAO.DAO;
import ru.koshkin.persist.DAO.LineItemDAO;
import ru.koshkin.persist.DAO.ProductDAO;
import ru.koshkin.persist.DAO.CustomerDAO;
import ru.koshkin.persist.Entities.Persistable;
import ru.koshkin.persist.Entities.ProductEntity;
import ru.koshkin.persist.Entities.CustomerEntity;

@Configuration
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean(name = "productDAO")
    public DAO productDAO() {
        return new ProductDAO();
    }

    @Bean(name = "customerDAO")
    public DAO userDAO() {
        return new CustomerDAO();
    }

    @Bean(name = "lineItemDAO")
    public DAO lineItemDAO() {
        return new LineItemDAO();
    }

    @Bean(name = "productEntity")
    public Persistable product() {
        return new ProductEntity();
    }

    @Bean(name = "userEntity")
    public Persistable user() {
        return new CustomerEntity();
    }


}
