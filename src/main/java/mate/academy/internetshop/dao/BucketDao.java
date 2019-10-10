package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketDao {
    Bucket add(Bucket bucket);

    Bucket get(Long id);

    Bucket getBucket(Long userId);

    List<Item> getAllItems(Long bucketId);

    Bucket addItem(Long bucketId, Item item);

    Bucket update(Bucket bucket);

    Bucket clear(Long id);

    void delete(Long id);

    Bucket deleteItem(Long bucketId, Long itemId);
}
