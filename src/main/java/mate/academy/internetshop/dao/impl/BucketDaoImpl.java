package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket add(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long id) {
        return Storage.buckets.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with id " + id));
    }

    @Override
    public Bucket getBucket(Long userId) {
        return Storage.buckets.stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with userId " + userId));
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        return null;
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket updatedBucket = get(bucket.getId());
        updatedBucket.setItems(bucket.getItems());
        return updatedBucket;
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        return null;
    }

    @Override
    public Bucket clear(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Storage.buckets
                .removeIf(b -> b.getId().equals(id));
    }

    @Override
    public Bucket deleteItem(Long bucketId, Long itemId) {
        return null;
    }
}
