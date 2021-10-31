package bakendi.restful.service;

import bakendi.restful.persistence.entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User update(User changedUser);
    void delete(User user);
    User createNewUser(User user);
    User findById(long id);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    String getTokenForUser(User user, String pwd);
}
