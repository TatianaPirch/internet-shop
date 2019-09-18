package mate.academy.internetshop.controller;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllOrdersController extends HttpServlet {
    private static final Long userId = 0L;

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orders = orderService.getAllOrdersForUser(userId);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}