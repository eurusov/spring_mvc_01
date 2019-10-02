package system.dao;

import system.model.User;

import java.util.List;

public interface UserDao {

    // create
    Long addUser(User user);

    // read
    User getUser(Long id);

    List<User> getAllUser();

    // update
    boolean updateUser(User user);

    // delete
    boolean deleteUser(Long id);

    User getUser(String email, String password);
}
