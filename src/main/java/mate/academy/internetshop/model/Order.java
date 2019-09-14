package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private static long idGenerator = 0;
    private Long id;
    private List<Item> items;
    private User user;

    public Order(List<Item> items, User user) {
        id = idGenerator++;
        this.items = items;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order id = " + id + ", user id = " + user.getId() + "\n" + items;
    }
}

