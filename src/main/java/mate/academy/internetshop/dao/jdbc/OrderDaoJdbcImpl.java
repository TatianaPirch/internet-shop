package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    public List<Item> addItemsInDb(Order order) {
        List<Item> items = order.getItems();
        for (Item item : items) {
            String query = "INSERT INTO orders_items (order_id, item_id) VALUES (?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, order.getId());
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Can't add items for order with id = " + order.getId(), e);
            }
        }
        return items;
    }

    public List<Item> getItemsFromDb(Long orderId) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items INNER JOIN orders_items"
                + " USING (item_id) WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't get items for order with id = " + orderId, e);
        }
        return items;
    }

    public void deleteItemFromDB(Long orderId) {
        String query = "DELETE FROM orders_items WHERE order_id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete order with id = " + orderId, e);
        }
    }

    @Override
    public Order add(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement statement = connection.prepareStatement(
                query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long orderId = generatedKeys.getLong(1);
            order.setId(orderId);
            addItemsInDb(order);
        } catch (SQLException e) {
            logger.error("Can't create order", e);
        }
        return order;
    }

    @Override
    public Order get(Long orderId) {
        Order order = null;
        String query = "SELECT orders.order_id, orders.user_id "
                + "FROM orders  WHERE orders.order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            List<Item> items = getItemsFromDb(orderId);
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                order = new Order(items, userId);
                order.setId(orderId);
                getItemsFromDb(orderId);
            }
            order.setItems(items);
        } catch (SQLException e) {
            logger.error("Can't get order by id = " + orderId, e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update order with id = " + order.getId(), e);
        }
        return order;
    }

    @Override
    public void delete(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            deleteItemFromDB(orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete order", e);
        }
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                orders.add(get(orderId));
            }
        } catch (SQLException e) {
            logger.error("Can't get orders for user with id = " + userId, e);
        }
        return orders;
    }
}
