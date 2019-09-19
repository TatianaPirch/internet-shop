package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static long idGenerator = 0;
    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket() {
        id = idGenerator++;
        items = new ArrayList<>();
    }

    public Bucket(Long userId) {
        this();
        this.userId = userId;

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
