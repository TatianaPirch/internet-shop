package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;

public class DeleteBucketItemController extends HttpServlet {

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        String itemId = req.getParameter("item_id");
        Bucket bucket = bucketService.getBucket(userId);
        bucketService.deleteItem(Long.valueOf(bucket.getId()),
                Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllBucketItems");
    }
}
