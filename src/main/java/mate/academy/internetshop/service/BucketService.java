package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.Bucket;

public interface BucketService {

    Bucket create(Bucket bucket);

    Bucket get(Long id);

    Bucket getBucket(Long userId);

    Bucket update(Bucket bucket);

    void delete(Long id);

    Bucket addItem(Long bucketId, Long itemId);

    Bucket deleteItem(Long bucketId, Long itemId);

    Bucket clear(Long bucketId);

    List getAllItems(Long bucketId);
}
