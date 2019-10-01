package system.service;

import org.springframework.stereotype.Service;
import system.dao.UserDao;
import system.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
