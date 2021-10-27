package bakendi.restful.persistence.repositories;

import bakendi.restful.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username);
}
