package mate.academy.internetshop.dao;

import java.util.Set;

import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {

    Set<Role> addRoleToDB(User user);

    void deleteRoleToDB(Long userId);

    Role getRoleByName(Role.RoleName roleName);
}
