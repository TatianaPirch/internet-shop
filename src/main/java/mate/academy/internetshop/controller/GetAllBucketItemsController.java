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

public class GetAllBucketItemsController extends HttpServlet {
    private static final Long USER_ID = 0L;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> allItems = bucketService.getAllItems(USER_ID);
        req.setAttribute("items", allItems);
        req.getRequestDispatcher("/WEB-INF/views/allBucketItems.jsp").forward(req, resp);
    }
}

