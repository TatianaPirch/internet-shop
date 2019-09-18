package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.service.BucketService;

public class DeleteBucketItemController extends HttpServlet {
    private static final Long USER_ID = 0L;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        bucketService.deleteItem(Long.valueOf(bucketService.getBucket(USER_ID).getId()),
                Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllBucketItems");
    }
}
