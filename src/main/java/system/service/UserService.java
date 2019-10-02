package system.service;

import system.model.User;

import java.util.List;

public interface UserService {

    Long addUser(User user);

    User getUser(Long id);

    User getUser(String email, String password);

    List<User> getAllUser();

    boolean updateUser(User user);

    boolean deleteUser(Long id);

}
