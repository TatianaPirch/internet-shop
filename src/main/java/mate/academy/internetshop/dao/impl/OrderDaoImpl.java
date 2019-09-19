package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order add(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Order get(Long id) {
        return Storage.orders.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find order with id " + id));
    }

    @Override
    public Order update(Order order) {
        Order updatedOrder = get(order.getId());
        updatedOrder.setItems(order.getItems());
        return updatedOrder;
    }

    @Override
    public void delete(Long id) {
        Storage.orders
                .removeIf(o -> o.getId().equals(id));
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
