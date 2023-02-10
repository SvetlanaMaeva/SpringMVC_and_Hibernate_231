package hiber.service;

import hiber.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public interface UserService {
    void add(User user);
    void delete(int id);
    void update(int id, User user);
    User getUser(long id);
    List<User> listUsers();

}
