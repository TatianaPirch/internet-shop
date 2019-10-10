package mate.academy.internetshop.dao.hibernate;

import java.util.Set;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoHibernateImpl.class);

    @Override
    public Set<Role> addRoleToDB(User user) {
        return null;
    }

    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Role WHERE roleName =:roleName");
            query.setParameter("roleName", roleName);
            Role role = (Role) query.uniqueResult();
            return role;
        }
    }

    @Override
    public void deleteRoleToDB(Long userId) {
    }
}
