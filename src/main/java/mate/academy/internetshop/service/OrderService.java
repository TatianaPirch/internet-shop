package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;

public interface OrderService {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    void delete(Long id);

    Order completeOrder(List<Item> items, Long userId);

    List<Order> getAllOrdersForUser(Long userId);
}
