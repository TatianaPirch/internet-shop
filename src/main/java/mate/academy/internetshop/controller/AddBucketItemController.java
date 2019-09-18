package mate.academy.internetshop.controller;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBucketItemController extends HttpServlet {
    private static final Long userId = 0L;
    private static final Long userBucketId = 0L;

    @Inject
    static BucketService bucketService;

    Bucket userBucket = new Bucket(userId);

    {
        bucketService.create(userBucket);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        bucketService.addItem(userBucketId, Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}








