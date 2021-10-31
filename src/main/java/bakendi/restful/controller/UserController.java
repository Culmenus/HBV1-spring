package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.security.JWTUtils;
import bakendi.restful.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    UserService userService;
    JWTUtils jwtUtils;
    @Autowired
    public UserController(UserService userService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    // End points to add
    // signup(GET, POST)
    // login(GET, POST)

    @PostMapping("/api/login")
    public String login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        try {
            User user = userService.findByUsername(username);
            String token = userService.getTokenForUser(user,pwd);
            if (token != null) {
                return token;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password incorrect");
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password incorrect");
        }
    }

    // loggedIn(GET)

    @GetMapping("/api/user")
    List<User> getAll() {
        return this.userService.findAll();
    }

    @PostMapping("/api/user")
    User createUser(@RequestBody User user) {
        if (!isValidUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username invalid");
        }
        // krabbamein?
        if (!isValidPassword(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password invalid");
        }
        userService.save(user);
        return user;
    }

    @GetMapping("/api/user/{id}")
    User findUserById(@PathVariable("id") long id) {
        System.out.println("yo");
        return this.userService.findById(id);
    }

    /**
     * Tekur inn user úr request body og breytir nafni og/eða lykilorði
     * ef þau hafa breyst og til var user með sama id og var sendur inn
     * @param changedUser
     * @return changedUser from repository layer
     */
    @PatchMapping("/api/user/updateuser")
    public User update(@RequestBody User changedUser) {
        return userService.update(changedUser);
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
        User user = userService.findByUsername(username);
        return user != null && user.getUsername().equals(username);
    }



    @GetMapping("/user/loggedin")
    User getLoggedIn(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null){
            return null;
        }
        else{
            token = token.replace("Bearer ", "");
            long userID = jwtUtils.getUserIDFromToken(token);
            return this.userService.findById(userID);
        }
    }
}
