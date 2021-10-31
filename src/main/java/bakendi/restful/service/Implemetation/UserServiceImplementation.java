package bakendi.restful.service.Implemetation;
import bakendi.restful.security.JWTUtils;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.repositories.UserRepository;
import bakendi.restful.service.UserService;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;
    private JWTUtils jwtUtils;
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String getTokenForUser(User user, String pwd) {
        if (user != null && user.getPassword().equals(pwd)) {
            String token = jwtUtils.generateToken(user);
            return token;
        }
        return null;
    }
}
