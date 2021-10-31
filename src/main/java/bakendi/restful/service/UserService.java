package bakendi.restful.service;

import bakendi.restful.persistence.entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);
    User findById(long id);
    User findByUsername(String username);
    List<User> findAll();
    String getTokenForUser(User user, String pwd);
}
