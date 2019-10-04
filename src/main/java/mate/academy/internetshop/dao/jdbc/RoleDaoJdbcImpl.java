package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class RoleDaoJdbcImpl  extends AbstractDao<Role> implements RoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoJdbcImpl.class);

    public RoleDaoJdbcImpl(Connection connection) {
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
                logger.error("Can't add role from user "
                        + "with login = " + user.getLogin(), e);
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
            logger.error("Can't delete role for user with id" + userId, e);
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
}