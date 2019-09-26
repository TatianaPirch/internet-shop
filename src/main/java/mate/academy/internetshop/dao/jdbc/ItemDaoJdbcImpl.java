package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final String DB_NAME = "internet_shop";
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item add(Item item) {
        Statement statement = null;
        String query = String.format("INSERT %s.items (name, price) VALUES ('%s', '%.2f');",
                DB_NAME, item.getName(), item.getPrice());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Can't add item " + item.getName(), e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        Statement statement = null;
        String query = String.format("SELECT * FROM %s.items WHERE item_id = %d;", DB_NAME, id);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.error("Can't get item by id" + id, e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        Statement statement = null;
        String query = String
                .format("UPDATE %s.items SET name  = '%s', price= %.2f WHERE item_id = %d;",
                        DB_NAME, item.getId(), item.getName(), item.getPrice(), item.getId());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Can't update item by id " + item.getId(), e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Statement statement = null;
        String query = String.format("DELETE FROM %s.items WHERE item_id = %d;", DB_NAME, id);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Can't delete item by id" + id, e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Can't close statement", e);
                }
            }
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Statement statement = null;
        String query = String.format("SELECT * FROM %s.items;", DB_NAME);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't get items", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Can't close statement", e);
                }
            }
        }
        return items;
    }
}
