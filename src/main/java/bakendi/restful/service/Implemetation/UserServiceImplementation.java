package bakendi.restful.service.Implemetation;
import bakendi.restful.security.JWTUtils;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.repositories.UserRepository;
import bakendi.restful.service.UserService;
import bakendi.restful.utilities.Utils;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.lang3.RandomStringUtils;

import java.net.http.HttpRequest;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final Utils utils;
    @Autowired
    public UserServiceImplementation(UserRepository userRepository, JWTUtils jwtUtils, Utils utils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.utils = utils;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User changedUser) {
        User oldUser = this.findById(changedUser.getID());
        String newUsername = changedUser.getUsername();
        String newPassword = changedUser.getPassword();
        boolean diffName = newUsername != null && !newUsername.equals(oldUser.getUsername());
        boolean diffPw =  newPassword != null && !newPassword.equals(oldUser.getPassword());
        if (diffName){
            System.out.println("username different");
            if (usernameExists(newUsername)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username already exists");
            }
            else if (isValidUsername(newUsername)) {
                // getum bara breytt username her, ekki öllum user
                oldUser.setUsername(newUsername);
            } else
                // skilum gamla user obreyttur ef username invalid
                // kasta error?
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username invalid");
        }
        if (diffPw) {
            System.out.println("password different");
            if (isValidPassword(newPassword)) {
                // save á að updatea user ef hann er til staðar
                // látum client bera ábyrgð á að ná fyrst í user by id
                oldUser.setPassword(newPassword);
            } else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password invalid");
        }
        // update with new usrname/password
        if (diffName || diffPw)
            this.save(oldUser);

        return oldUser;
    }

    @Override
    public String sendEmailVerificationForUser(User user, String url) {
        if (!isValidUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username invalid");
        }
        // krabbamein?
        if (!isValidPassword(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password invalid");
        }
        // viljum vid ad username megi vera duplicate?
        if (usernameExists(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username already exists");
        }
        if (emailExists(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email already exists");
        }
        //this.save(user); Save user after verifying email.
        String key = RandomStringUtils.random(20);
        url = url + '/' + key;
        utils.sendMail(url,user.getEmail());
        return key;
    }

    /**
     * Validates username
     * @param username
     * @return true if username between 3 og 20 chars long, begins on a letter
     *  then contains letters, numbers or underscores only.
     */
    private boolean isValidUsername(String username) {
        String regularExpression = "^[a-zA-Z][a-zA-Z0-9_]{2,19}$";
        return username.matches(regularExpression);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 1;
    }

    private boolean usernameExists(String username) {
        User user = this.findByUsername(username);
        return user != null && user.getUsername().equals(username);
    }

    private boolean emailExists(String email) {
        User user = this.findByUsername(email);
        return user != null && user.getEmail().equals(email);
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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
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
