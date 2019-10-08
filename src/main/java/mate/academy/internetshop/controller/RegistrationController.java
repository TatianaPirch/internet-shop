package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (userService.getByLogin(req.getParameter("login")) == null) {
            User newUser = new User();
            newUser.setLogin(req.getParameter("login"));
            newUser.setName(req.getParameter("user_name"));
            newUser.setSurname(req.getParameter("user_surname"));
            newUser.setPassword(req.getParameter("psw"));
            User user = userService.create(newUser);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("errorMsg", "Login already exists. Please enter another login!");
            req.getRequestDispatcher("WEB-INF/views/registration.jsp").forward(req,resp);
        }
    }
}

