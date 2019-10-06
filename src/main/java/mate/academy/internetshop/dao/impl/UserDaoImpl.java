package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.model.User;

public class UserDaoImpl implements UserDao {

    @Override
    public User add(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public User get(Long id) {
        return Storage.users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find user with id " + id));
    }

    @Override
    public User update(User user) {
        User updatedUser = get(user.getId());
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        Storage.users
                .removeIf(u -> u.getId().equals(id));
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User getByLogin(String login) throws AuthenticationException {
        Optional<User> user = Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        if (user.isEmpty()) {
            throw new AuthenticationException("incorrect login");
        }
        return user.get();
    }

    @Override
    public boolean uniqueLogin(String login) {
        Optional<User> user = Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        if (user.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
