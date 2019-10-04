package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket(Long userId) {
        items = new ArrayList<>();
        this.userId = userId;
    }

    public Bucket(Long userId, Long id) {
        items = new ArrayList<>();
        this.userId = userId;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
