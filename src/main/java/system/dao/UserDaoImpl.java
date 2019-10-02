package system.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(user);
    }

    @Override
    public User getUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUser() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User").list();
    }

    /* Updates only fields that are not null in updatedUser */
    @Override
    public boolean updateUser(User updatedUser) {
        Session session = sessionFactory.getCurrentSession();
        User oldUser = session.get(User.class, updatedUser.getId());
        if (oldUser == null) {
            return false;
        }
        String param;
        if ((param = updatedUser.getEmail()) != null) {
            oldUser.setEmail(param);
        }
        if ((param = updatedUser.getPassword()) != null) {
            oldUser.setPassword(param);
        }
        if ((param = updatedUser.getFirstName()) != null) {
            oldUser.setFirstName(param);
        }
        if ((param = updatedUser.getLastName()) != null) {
            oldUser.setLastName(param);
        }
        if ((param = updatedUser.getCountry()) != null) {
            oldUser.setCountry(param);
        }
        if ((param = updatedUser.getRole()) != null) {
            oldUser.setRole(param);
        }
        return true;
    }

    @Override
    public boolean deleteUser(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User userToDelete = session.get(User.class, id);
        if (userToDelete == null) {
            return false;
        }
        session.delete(userToDelete);
        return true;
    }

    @Override
    public User getUser(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE email=:e_mail and password=:pass");
        query.setParameter("e_mail", email);
        query.setParameter("pass", password);
        return (User) query.uniqueResult();
    }
}
