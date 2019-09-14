package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static String name;
    private static long idGenerator = 0;
    private Long id;
    private List<Order> orders;
    private Bucket bucket;

    public User(String name) {
        this.name = name;
        id = idGenerator++;
        orders = new ArrayList<>();
        this.bucket = new Bucket(this);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }
}
