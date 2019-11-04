package mate.academy.internetshop.dao;

import java.util.Set;

import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {

    Set<Role> addRole(User user);

    void deleteAllRolesForUser(User user);

    Role getRoleByName(Role.RoleName roleName);
}
