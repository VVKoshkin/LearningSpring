package ru.koshkin;

import ru.koshkin.persist.User;
import ru.koshkin.persist.UserRepo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepo userRepo = new UserRepo();
        userRepo.insert(new User("User1"));
        userRepo.insert(new User("User2"));
        userRepo.insert(new User("User3"));
        userRepo.insert(new User("User4"));
        userRepo.insert(new User("User5"));
        userRepo.insert(new User("User6"));
        sce.getServletContext().setAttribute("userRepo", userRepo);
    }
}
