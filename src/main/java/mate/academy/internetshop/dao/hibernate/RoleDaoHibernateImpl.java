package mate.academy.internetshop.dao.hibernate;

import java.util.Set;

import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class RoleDaoHibernateImpl implements RoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoHibernateImpl.class);

    @Override
    public Set<Role> addRoleToDB(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            try {
                Query query = session.createSQLQuery(
                        "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)");
                query.setParameter(1, user.getId());
                query.setParameter(2, role.getId());
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                logger.error("Can't add role from user "
                        + "with login = " + user.getLogin(), e);
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return roles;
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery(
                    "DELETE FROM users_roles WHERE user_id = ?");
            query.setParameter(1, userId);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete role for user with id" + userId, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
