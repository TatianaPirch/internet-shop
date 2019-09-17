package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static long idGenerator = 0;
    private Long id;
    private List<Item> items;
    private final Long userId;

    public Bucket(Long userId) {
        this.userId = userId;
        id = idGenerator++;
        items = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void clear() {
        items = new ArrayList<>();
    }
}
