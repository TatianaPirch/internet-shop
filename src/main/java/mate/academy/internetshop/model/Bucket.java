package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static long idGenerator = 0;
    private Long id;
    private List<Item> items;
    private User user;

    public Bucket(User user) {
        id = idGenerator++;
        items = new ArrayList<>();
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
