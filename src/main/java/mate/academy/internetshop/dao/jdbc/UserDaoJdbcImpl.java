package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static RoleDao roleDao;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    private User userBuild(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String token = resultSet.getString("token");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, surname, login, password);
        user.setId(userId);
        user.setToken(token);
        user.setRoles(roleDao.getRolesDb(user));
        return user;
    }

    @Override
    public User add(User user) {
        String query = "INSERT INTO users (name, surname, password, login, token)"
                + " VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getToken());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long userId = generatedKeys.getLong(1);
            user.setId(userId);
            user.addRole(Role.of("USER"));
            roleDao.addRoleToDB(user);
            return user;
        } catch (SQLException e) {
            logger.error("Can't add user with login = " + user.getLogin(), e);
        }
        return user;
    }

    @Override
    public User get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return userBuild(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Can't get user by id = " +  id, e);
        }
        return null;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET name  = ?, surname = ?, password = ?, login = ? "
                + " WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setLong(5,  user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.error("Can't update user by id = " + user.getId(), e);
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            roleDao.deleteRoleToDB(id);
            List<Order> orders = orderDao.getAllOrdersForUser(id);
            for (Order order: orders) {
                orderDao.delete(order.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete user by id = " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(userBuild(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't get users", e);
        }
        return users;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        String query = "SELECT * FROM users WHERE login = ? AND password = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return userBuild(resultSet);
            }
        } catch (SQLException e) {
            logger.warn("Can’t get user with login = " + login);
        }
        throw new AuthenticationException("Can’t get user with login = " + login);
    }

    @Override
    public Optional<User> getByToken(String token) {
        List<User> users = getAll();
        return users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
