package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class AddBucketItemController extends HttpServlet {

    @Inject
    static BucketService bucketService;

    @Inject
    static UserService userService;

    @Inject
    static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        String itemId = req.getParameter("item_id");
        User user = userService.get(userId);
        Bucket bucket = bucketService.getBucket(userId);
        Item item = itemService.get(Long.valueOf(itemId));
        bucketService.addItem(bucket.getId(), item);
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
