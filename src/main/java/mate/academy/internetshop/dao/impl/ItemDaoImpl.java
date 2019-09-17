package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item add(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Item get(Long id) {
        return Storage.items.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find item with id " + id));
    }

    @Override
    public Item update(Item item) {
        Item updatedItem = get(item.getId());
        updatedItem.setName(item.getName());
        updatedItem.setPrice(item.getPrice());
        return updatedItem;
    }

    @Override
    public void delete(Long id) {
        Storage.items
                .removeIf(i -> i.getId().equals(id));
    }

    @Override
    public void delete(Item item) {
        Storage.items
                .removeIf(e -> e.equals(item));
    }
}
