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
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    public Set<Role> addRoleToDB(User user) {
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            String query = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?) ";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, user.getId());
                statement.setLong(2, 1);
                statement.executeUpdate();
                return roles;
            } catch (SQLException e) {
                logger.error("Can't add role from user " +
                        "with login = " + user.getLogin(), e);
            }
        }
        return roles;
    }

    public void deleteRoleToDB(Long userId) {
        String query = "DELETE FROM users_roles WHERE user_id = ? ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete role with id" + userId, e);
        }
    }

    public Set<Role> getRolesDb(User user) {
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
            addRoleToDB(user);
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
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String token = resultSet.getString("token");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(name, surname, login, password);
                user.setId(id);
                user.setToken(token);
                user.setRoles(getRolesDb(user));
                return user;
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
            statement.setLong(5, id);
            statement.executeUpdate();
            deleteRoleToDB(id);
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
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String token = resultSet.getString("token");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(name, surname, login, password);
                user.setId(userId);
                user.setToken(token);
                user.setRoles(getRolesDb(user));
                users.add(user);
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
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String token = resultSet.getString("token");
                User user = new User(name, surname, login, password);
                user.setId(userId);
                user.setToken(token);
                user.setRoles(getRolesDb(user));
                return user;
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
