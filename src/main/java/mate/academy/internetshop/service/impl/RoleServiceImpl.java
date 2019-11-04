package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Inject
    private static RoleDao roleDao;

    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        return roleDao.getRoleByName(roleName);
    }
}
