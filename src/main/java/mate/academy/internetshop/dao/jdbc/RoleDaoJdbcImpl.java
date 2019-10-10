package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

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

    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        String getRoleQuery = "SELECT * FROM roles WHERE name=?";
        try (PreparedStatement statement =
                     connection.prepareStatement(getRoleQuery)) {
            statement.setString(1, String.valueOf(roleName));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleNameDb = resultSet.getString("name");
                Role role = Role.of(roleNameDb);
                role.setId(roleId);
                return role;
            }
        } catch (SQLException e) {
            logger.error("Can’t get role with name= " + roleName, e);
        }
        return null;
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
}
