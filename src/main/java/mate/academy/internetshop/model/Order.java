package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private static long idGenerator = 0;
    private Long id;
    private List<Item> items;
    private Long userId;

    public Order(List<Item> items, Long userId) {
        id = idGenerator++;
        this.items = items;
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

    @Override
    public String toString() {
        return "Order id = " + id + ", user id = " + userId + "\n" + items;
    }
}

