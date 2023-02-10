package hiber.dao;

import hiber.model.User;


import java.util.List;

public interface UserDao {
    void add(User user);

    void delete(int id);

    void update(int id, User user);

    User getUser(long id);

    List<User> listUsers();
}
