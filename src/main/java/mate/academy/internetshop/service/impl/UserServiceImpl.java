package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.annotation.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private static UserDao userDao;
    @Inject
    private static BucketDao bucketDao;

    @Override
    public User create(User user) {
        User newUser = userDao.add(user);
        bucketDao.add(newUser.getBucket());
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
        userDao.delete(id);
    }

    @Override
    public List getOrders(User user) {
        return userDao.get(user.getId()).getOrders();
    }
}
