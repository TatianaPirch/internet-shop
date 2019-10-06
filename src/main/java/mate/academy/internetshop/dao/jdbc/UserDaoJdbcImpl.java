package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HashUtil;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    private Set<Role> returnRolesForUser(User user) {
        Set<Role> roles = new HashSet<>();
        String query = "SELECT * FROM roles INNER JOIN users_roles"
                + " USING (role_id) WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleDb = resultSet.getString("role_name");
                Role role = Role.of(roleDb);
                roles.add(role);
                return roles;
            }
        } catch (SQLException e) {
            logger.error("Can’t get role for user with login = " + user.getLogin(), e);
        }
        return roles;
    }

    private User userBuild(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String token = resultSet.getString("token");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, surname, login, password);
        user.setId(userId);
        user.setToken(token);
        user.setSalt(salt);
        user.setRoles(returnRolesForUser(user));
        return user;
    }

    @Override
    public User add(User user) {
        String query = "INSERT INTO users (name, surname, password, login, token, salt)"
                + " VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            byte[] salt = HashUtil.getSalt();
            String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, hashPassword);
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getToken());
            statement.setBytes(6, salt);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            Long userId = generatedKeys.getLong(1);
            user.setId(userId);
            user.addRole(Role.of("USER"));
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
        String query = "UPDATE users SET name  = ?, surname = ?, password = ?, login = ?, salt = ? "
                + " WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            byte[] salt = HashUtil.getSalt();
            String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, hashPassword);
            statement.setString(4, user.getLogin());
            statement.setBytes(5, salt);
            statement.setLong(6, user.getId());
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
    public User getByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return userBuild(resultSet);
            }
        } catch (SQLException e) {
            logger.warn("Can’t get user with login = " + login);
        }
        return null;
    }

    @Override
    public Optional<User> getByToken(String token) {
        List<User> users = getAll();
        return users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
