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

    @GetMapping("api/user")
    List<User> getAll() {
        return this.userService.findAll();
    }

    @GetMapping("api/user/{id}")
    User findUserById(@PathVariable("id") long id) {
        return this.userService.findById(id);
    }

    @PatchMapping("api/user/changepassword")
    public User changePassword(User changedUser) {
        if (isValidPassword(changedUser.getPassword())) {
            // save á að updatea user ef hann er til staðar
            // látum client bera ábyrgð á að ná fyrst í user by id
            userService.save(changedUser);
            return changedUser;
        } else
            // skilum gamla user obreyttur ef password invalid
            return findUserById(changedUser.getID());
    }

    @PatchMapping("api/user/changeusername")
    public User changeUsername(User changedUser) {
        if (isValidUsername(changedUser.getUsername())) {
            // save á að updatea user ef hann er til staðar
            // látum client bera ábyrgð á að ná fyrst í user by id
            userService.save(changedUser);
            return changedUser;
        } else
            // skilum gamla user obreyttur ef username invalid
            // kasta error?
            return findUserById(changedUser.getID());
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
        if (password.length() < 5)
            return false;
        else
            return true;
    }



}
