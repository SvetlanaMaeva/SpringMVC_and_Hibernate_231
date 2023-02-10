package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Repository
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao UserDao;

    @Transactional
    @Override
    public void delete(int id) {
        UserDao.delete(id);
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        UserDao.update(id, user);
    }

    @Transactional
    @Override
    public void add(User user) {
        UserDao.add(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return UserDao.getUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return UserDao.listUsers();
    }

}
