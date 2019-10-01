package system.dao;

import system.model.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserDao {
    private List<User> users = Arrays.asList(
            new User("admin", "1"),
            new User("user1", "2")
    );

    public List<User> getAllUsers() {
        return users;
    }
}

