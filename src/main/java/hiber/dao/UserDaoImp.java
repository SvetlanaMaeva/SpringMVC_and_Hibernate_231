package hiber.dao;

import hiber.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void delete(int id) {
        List<User> users = listUsers();
        for (User user : users) {
            if (user.getId() == id){
                entityManager.remove(user);
            }
        }
    }

    @Transactional
    public void update(int id, User user) {
        User newUser = getUser(id);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
    }

    @Transactional
    public User getUser(long id) {
        return entityManager.createQuery("FROM User user WHERE user.id = :id", User.class)
                .setParameter("id", id).getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> listUsers() {
        Query query = entityManager.createQuery("from User", User.class);
        return query.getResultList();

    }
}
