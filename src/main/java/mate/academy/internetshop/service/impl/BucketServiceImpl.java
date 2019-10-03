package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.add(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id);
    }

    @Override
    public Bucket getBucket(Long userId) {
        return bucketDao.getBucket(userId);
    }

    @Override
    public void delete(Long id) {
        bucketDao.delete(id);
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        return bucketDao.addItem(bucketId, itemId);
    }

    @Override
    public Bucket deleteItem(Long bucketId, Long itemId) {
        return bucketDao.deleteItem(bucketId, itemId);
    }

    @Override
    public Bucket clear(Long bucketId) {
        return bucketDao.clear(bucketId);
    }

    @Override
    public List getAllItems(Long bucketId) {
        return bucketDao.getAllItems(bucketId);
    }
}
