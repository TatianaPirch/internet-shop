package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class BucketDaoHibernateImpl implements BucketDao {
    private static Logger logger = Logger.getLogger(BucketDaoHibernateImpl.class);

    @Override
    public Bucket add(Bucket bucket) {
        Long bucketId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucketId = (Long) session.save(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create bucket", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        bucket.setId(bucketId);
        return bucket;
    }

    @Override
    public Bucket get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = session.get(Bucket.class, id);
            return bucket;
        }
    }

    @Override
    public Bucket getBucket(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Bucket WHERE userId = :userId ");
            query.setParameter("userId", userId);
            return (Bucket) query.uniqueResult();
        }
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return get(bucketId).getItems();
    }

    @Override
    public Bucket addItem(Long bucketId, Item item) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId);
            List<Item> items = bucket.getItems();
            items.add(item);
            bucket.setItems(items);
            update(bucket);
        } catch (Exception e) {
            logger.error("Can't add item to bucket ", e);
        }
        return get(bucketId);
    }

    @Override
    public Bucket update(Bucket bucket) {
        Transaction transaction = null;
        Session session = null;
        try  {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update Bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public Bucket clear(Long bucketId) {
        try {
            Bucket bucket = get(bucketId);
            List<Item> items = bucket.getItems();
            items = new ArrayList();
            bucket.setItems(items);
            update(bucket);
        } catch (Exception e) {
            logger.error("Can't clear bucket with id = " + bucketId, e);
        }
        return get(bucketId);
    }

    @Override
    public void delete(Long id) {
        Bucket bucket = get(id);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete bucket with id = " + id, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Bucket deleteItem(Long bucketId, Long itemId) {
        Bucket bucket = get(bucketId);
        try {
            List<Item> items = bucket.getItems();
            for (Item item : items) {
                if (item.getId().equals(itemId)) {
                    items.remove(item);
                    break;
                }
            }
            bucket.setItems(items);
            update(bucket);
        } catch (Exception e) {
            logger.error("Can't delete item from bucket with id = " + bucketId, e);
        }
        return get(bucketId);
    }
}
