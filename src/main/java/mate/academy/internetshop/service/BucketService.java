package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService {

    Bucket create(Bucket bucket);

    Bucket get(Long id);

    Bucket getBucket(Long userId);

    void delete(Long id);

    Bucket addItem(Long bucketId, Item item);

    Bucket deleteItem(Long bucketId, Long itemId);

    Bucket clear(Long bucketId);

    List getAllItems(Long bucketId);
}
