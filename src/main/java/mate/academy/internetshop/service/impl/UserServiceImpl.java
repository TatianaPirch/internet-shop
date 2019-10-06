package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exception.AuthenticationException;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;
    @Inject
    private static BucketDao bucketDao;
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static RoleDao roleDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        User newUser = userDao.add(user);
        roleDao.addRoleToDB(newUser);
        return newUser;
    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        bucketDao.delete(bucketDao.getBucket(id).getId());
        roleDao.deleteRoleToDB(id);
        orderDao.getAllOrdersForUser(id)
                .forEach(o -> orderDao.delete(o.getId()));
        userDao.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException {
        byte[] salt = userDao.getByLogin(login).getSalt();
        if (!HashUtil.hashPassword(password, salt)
                .equals(userDao.getByLogin(login).getPassword())) {
            throw new AuthenticationException("incorrect login or password");
        }
        return userDao.getByLogin(login);
    }

    @Override
    public boolean uniqueLogin(String login) {
        return userDao.uniqueLogin(login);
    }

    @Override
    public Optional<User>  getByToken(String token) {
        return userDao.getByToken(token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }
}
