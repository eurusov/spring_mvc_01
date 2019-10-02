package system.service;

import org.springframework.stereotype.Service;
import system.dao.UserDao;
import system.model.User;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {this.userDao = userDao;}

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
