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

public class CreateDataController extends HttpServlet {

    @Inject
    private static ItemService itemService;

    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Item pen = itemService.create(new Item("Pen", 10.));
        Item notebook = itemService.create(new Item("Notebook", 50.));
        Item folder = itemService.create(new Item("Folder", 100.));

        User tania = userService.create(new User("Tania",
                "Pirch", "Student1"));

        Bucket taniaBucket = new Bucket(tania.getId());
        bucketService.create(taniaBucket);

        req.getRequestDispatcher("/WEB-INF/views/createData.jsp").forward(req, resp);
    }
}
