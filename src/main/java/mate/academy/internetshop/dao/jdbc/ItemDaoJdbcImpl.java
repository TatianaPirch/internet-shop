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
    private static String DB_NAME = "internet_shop";
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item add(Item item) {
        Statement stmt = null;
        String query = "INSERT " + DB_NAME + ".items (name, price) VALUES ('"
                + item.getName() + "', " + item.getPrice() + ");";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't add item " + item.getName(), e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        Statement stmt = null;
        String query = "SELECT * FROM " + DB_NAME + ".items WHERE item_id = " + id + ";";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id" + id, e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        Statement stmt = null;
        String query = "UPDATE " + DB_NAME + ".items"
                + " SET "
                + "name = '" + item.getName()
                + "', price = " + item.getPrice()
                + " WHERE item_id = " + item.getId() + ";";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't update item by id " + item.getId(), e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Statement stmt = null;
        String query = "DELETE FROM " + DB_NAME + ".items WHERE item_id = " + id + ";";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't delete item by id" + id, e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT * FROM " + DB_NAME + ".items;";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get items", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement", e);
                }
            }
        }
        return items;
    }
}
