package mate.academy.internetshop.controller;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllUsersController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.getAll();
        users.add(new User("Tania"));
        users.add(new User("Victor"));
        users.add(new User("Max"));
        req.setAttribute("greeting", "Mates");
        req.setAttribute("users", users);

        req.getRequestDispatcher("/WEB-INF/views/allUsers.jsp").forward(req,resp);
    }
}
