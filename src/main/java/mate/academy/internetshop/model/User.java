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

    public User() {
        id = idGenerator++;
        orders = new ArrayList<>();
    }

    public User(String name, String surname, String login) {
        this();
        this.name = name;
        this.surname = surname;
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name=" + name
                + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
