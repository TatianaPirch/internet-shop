package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.User;

public interface UserDao {

    User add(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAll();

    User getByLogin(String login);

    Optional<User> getByToken(String token);
}

