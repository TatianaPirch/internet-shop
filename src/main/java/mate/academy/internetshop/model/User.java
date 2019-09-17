package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private static long idGenerator = 0;
    private Long id;
    private List<Order> orders;
    private String surname;
    private String login;
    private String password;

    public User(String name) {
        this.name = name;
        id = idGenerator++;
        orders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
