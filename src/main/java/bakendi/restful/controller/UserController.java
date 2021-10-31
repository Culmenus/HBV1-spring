package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // End points to add
    // signup(GET, POST)
    // login(GET, POST)

    @PostMapping("api/login")
    public String login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        User user = userService.findByUsername(username);
        System.out.println(user.toString());
        if(user != null && user.getPassword().equals(pwd)){
            String token = getJWTToken(user.getID());
            return token;
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password incorrect");
        }
    }

    private String getJWTToken(long userID) {
        String secretKey = "oo323kjok2jerlkaldkjp98q2t4pulakfjlkdjhioa3ry223rfweak";
        Algorithm algorithmHS = Algorithm.HMAC512(secretKey);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                //TODO: Baeta vid user authority fra roles
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        //jwt token issued by the boys, userID can be gotten from token 'sub' claim, expires one week from now.
        String token = JWT.create().withIssuer("theBoys").withClaim("sub", userID)
                .withClaim("authorities",
                grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+ 600000000))
                .sign(algorithmHS);
        return token;
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

    @PatchMapping("/api/user/changepassword")
    public User changePassword(@RequestBody User changedUser) {
        User oldUser = userService.findById(changedUser.getID());
        String newPassword = changedUser.getUsername();
        if (isValidPassword(changedUser.getPassword())) {
            // save á að updatea user ef hann er til staðar
            // látum client bera ábyrgð á að ná fyrst í user by id
            userService.save(changedUser);
            return changedUser;
        } else
            // skilum gamla user obreyttur ef password invalid
            return findUserById(changedUser.getID());
    }

    @PatchMapping("/api/user/updateuser")
    public User update(@RequestBody User changedUser) {
        System.out.println(changedUser.getUsername());
        User oldUser = userService.findById(changedUser.getID());
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
            userService.save(oldUser);

        return oldUser;
    }

    /**
     * Validates username
     * @param username
     * @return true if username between 3 og 20 chars long, begins on a letter
     *  then contains letters, numbers or underscores only.
     */
    private boolean isValidUsername(String username) {
        String regularExpression = "^[a-zA-Z][a-zA-Z0-9_]{2,19}$";
        if (username.matches(regularExpression)) {
            return true;
        } else
            return false;

    }

    private boolean isValidPassword(String password) {
        if (password.length() < 1)
            return false;
        else
            return true;
    }

    private boolean usernameExists(String username) {
        if (userService.findByUsername(username)!=null && userService.findByUsername(username).getUsername().equals(username)) {
            return true;
        } else
            return false;
    }



}
