package mate.academy.internetshop.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mate.academy.internetshop.Main;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.factory.Factory;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class Injector {
    private static List<Class> classesNeedInjection = new ArrayList<>();
    private static Map<Class, Object> classesWithImplementation = new HashMap<>();

    static {
        classesNeedInjection.add(BucketServiceImpl.class);
        classesNeedInjection.add(ItemServiceImpl.class);
        classesNeedInjection.add(OrderServiceImpl.class);
        classesNeedInjection.add(UserServiceImpl.class);
        classesNeedInjection.add(Main.class);

        classesWithImplementation.put(ItemService.class, Factory.getItemService());
        classesWithImplementation.put(BucketService.class, Factory.getBucketService());
        classesWithImplementation.put(OrderService.class, Factory.getOrderService());
        classesWithImplementation.put(UserService.class, Factory.getUserService());
        classesWithImplementation.put(ItemDao.class, Factory.getItemDao());
        classesWithImplementation.put(BucketDao.class, Factory.getBucketDao());
        classesWithImplementation.put(OrderDao.class, Factory.getOrderDao());
        classesWithImplementation.put(UserDao.class, Factory.getUserDao());
    }

    public static void injectDependencies() throws IllegalAccessException {
        for (Class classNeedInjection : classesNeedInjection) {
            for (Field field : classNeedInjection.getDeclaredFields()) {
                if (field.getDeclaredAnnotation(Inject.class) != null
                        && classesWithImplementation.containsKey(field.getType())
                        && (classesWithImplementation.get(field.getType()).getClass()
                        .getDeclaredAnnotation(Dao.class) != null
                        || classesWithImplementation.get(field.getType()).getClass()
                        .getDeclaredAnnotation(Service.class) != null)) {
                    field.setAccessible(true);
                    field.set(null, classesWithImplementation.get(field.getType()));
                }
            }
        }
    }
}
