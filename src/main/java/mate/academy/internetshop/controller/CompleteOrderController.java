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
    private static final Long userId = 0L;
    private static final Long userBucketId = 0L;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = bucketService.getAllItems(userBucketId);
        orderService.completeOrder(items, userId);
        bucketService.clear(userBucketId);
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllOrders");
    }
}