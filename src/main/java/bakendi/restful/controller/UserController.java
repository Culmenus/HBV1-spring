package bakendi.restful.controller;

import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/login")
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

    @GetMapping("/user")
    List<User> getAll() {
        return this.userService.findAll();
    }
}
