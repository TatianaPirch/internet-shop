package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;

public class CompleteOrderController extends HttpServlet {
    private static final Long USER_ID = 0L;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = bucketService.getAllItems(bucketService.getBucket(USER_ID).getId());
        orderService.completeOrder(items, USER_ID);
        bucketService.clear(bucketService.getBucket(USER_ID).getId());
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllOrders");
    }
}
