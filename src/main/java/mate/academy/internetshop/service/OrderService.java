package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    void delete(Long id);

    Order completeOrder(List<Item> items, User user);

    List<Order> getAllOrdersForUser(Long userId);
}
