package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserService {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<User> getAll();

    User login(String login,String password) throws AuthenticationException;

    User getByLogin(String login);

    Optional<User> getByToken(String token);
}
