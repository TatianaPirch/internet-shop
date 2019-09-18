package mate.academy.internetshop.controller;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.service.BucketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBucketItemController extends HttpServlet {
    private static final Long userBucketId = 0L;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        bucketService.deleteItem(Long.valueOf(userBucketId), Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllBucketItems");
    }
}

