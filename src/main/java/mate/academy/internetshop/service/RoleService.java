package mate.academy.internetshop.service;

import mate.academy.internetshop.model.Role;

public interface RoleService {
    Role getRoleByName(Role.RoleName roleName);
}
