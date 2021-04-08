package ru.koshkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.koshkin.persist.User;
import ru.koshkin.persist.UserRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private UserRepo userRepo;

    @Override
    public void init() throws ServletException {
        logger.info("Initialized user repository!");
        this.userRepo = (UserRepo) getServletContext().getAttribute("userRepo");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        try {
            if (req.getPathInfo() == null) {
                logger.info("Request of user table");
                wr.println("<table>");
                wr.println("<tr>");
                wr.println("<th>Id</th>");
                wr.println("<th>Username</th>");
                wr.println("</tr>");
                for (User user : userRepo.findAll()) {
                    wr.println("<tr>");
                    wr.println("<td>" + user.getId() + "</td>");
                    wr.println(String.format("<td><a href=\"user/%d\">%s</a></td>", user.getId(), user.getUsername()));
                    wr.println("</tr>");
                }
                wr.println("</table>");
            } else if (req.getPathInfo().matches("/\\d+")) {
                Long id = Long.parseLong(req.getPathInfo().substring(1));
                logger.info("Requested user with ID=" + id);
                User user = userRepo.findById(id);
                if (user != null) {
                    String name = user.getUsername();
                    wr.println(String.format("<p>User ID: %d</p>", id));
                    wr.println(String.format("<h3>This page belongs to %s!", name));
                } else {
                    resp.setStatus(404);
                    wr.println("<h2>Oops! Wrong user</h2>");
                }
                wr.println(String.format("<p><a href='../user'>Go Back</a></p>"));
            } else {
                throw new UnsupportedOperationException("Проверка как логируются исключения");
            }
        } catch (Exception e) {
            logger.error("Error in UserServlet", e);
            resp.setStatus(500);
            wr.println("<h3 style=\"color: red;\">Internal server error :(</h3>");
        }
    }
}
