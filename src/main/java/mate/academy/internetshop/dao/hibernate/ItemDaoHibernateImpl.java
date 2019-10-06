package mate.academy.internetshop.dao.hibernate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.util.HibernateUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ItemDaoHibernateImpl implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoHibernateImpl.class);

    @Override
    public Item add(Item item) {
        Long itemId = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            itemId = (Long) session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't add item " + item.getName(), e);
        }
        item.setId(itemId);
        return item;
    }

    @Override
    public Item get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Item item = session.get(Item.class, id);
            return item;
        }
    }

    @Override
    public Item update(Item item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't update item " + item.getName(), e);
        }
        return item;
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(get(id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Can't delete item by id" + id, e);
        }
    }

    @Override
    public List<Item> getAllItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Item> criteria = builder.createQuery(Item.class);
            criteria.from(Item.class);
            return session.createQuery(criteria).getResultList();
        }
    }
}
