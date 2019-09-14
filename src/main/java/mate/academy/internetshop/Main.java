package mate.academy.internetshop;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.annotation.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    static {
        try {
            Injector.injectDependencies();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static UserService userService;
    @Inject
    private static OrderService orderService;
    @Inject
    private static ItemService itemService;
    @Inject
    private static BucketService bucketService;

    public static void main(String[] args) {

        User tania = userService.create(new User("Tania"));

        Bucket taniaBucket = bucketService.get(tania.getBucket().getId());

        Item pen = itemService.create(new Item("Pen", 10.));
        Item notebook = itemService.create(new Item("Notebook", 50.));
        Item folder = itemService.create(new Item("Folder", 100.));

        bucketService.addItem(taniaBucket.getId(), pen.getId());
        bucketService.addItem(taniaBucket.getId(), notebook.getId());
        bucketService.addItem(taniaBucket.getId(), folder.getId());

        orderService.completeOrder(taniaBucket.getItems(), taniaBucket.getUser());
    }
}
