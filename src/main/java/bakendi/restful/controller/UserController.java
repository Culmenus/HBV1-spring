package bakendi.restful.controller;


import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.security.JWTUtils;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    UserService userService;
    JWTUtils jwtUtils;
    @Autowired
    public UserController(UserService userService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    private class UserTokenPair{
        private final User user;
        private final String token;
        private final Set<Forum> favoriteForums;

        public UserTokenPair (User user, String token, Set<Forum> favoriteForums){
            this.user = user;
            this.token = token;
            this.favoriteForums = favoriteForums;
        }

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
        public Set<Forum> getFavoriteForums() { return favoriteForums;}
    }


    @PostMapping("/login")
    public UserTokenPair login(@RequestBody User userIn) {
        System.out.println(userIn.getEmail());
        try {
            String email = userIn.getEmail();
            String pwd = userIn.getPassword();
            User user = userService.findByEmail(email);
            String token = userService.getTokenForUser(user,pwd);
            if (token != null) {
                UserTokenPair out = new UserTokenPair(user, token, user.getFavoriteForums());
                return out;
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password incorrect");
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password incorrect");
        }
    }

    @GetMapping("/api/user")
    List<User> getAll() {
            return this.userService.findAll();
    }

    @PostMapping("/api/user/signup")
    User signup(@RequestBody User user,HttpSession session, HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + request.getServerPort() + "verify";
        String key = userService.sendEmailVerificationForUser(user,url);
        session.setAttribute(key,user);
       return user;
    }

    @GetMapping("/api/user/{id}")
    User findUserById(@PathVariable("id") long id) {
        return this.userService.findById(id);
    }


    /**
     * Takes in a User from request body and changes username and/or password
     * if they have changed and a user exists with same id as the one in request body.
     * @param changedUser
     * @return changedUser from repository layer
     */
    @PatchMapping("/api/user/updateuser")
    public User update(@RequestBody User changedUser, HttpSession session, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization token");
        User user = (User) session.getAttribute(token);
        if (user != null && user.getID() == changedUser.getID())
            return userService.update(changedUser);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user ID does not match logged in user");
    }

    @DeleteMapping("/api/user/delete")
    public void delete(HttpSession session, HttpServletRequest request) {
        // ensure admin?
        String token = request.getHeader("Authorization");
        if(token == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization token");
        User user= (User) session.getAttribute(token);
        if(user.getUserRole() == UserRole.ROLE_ADMIN){
            userService.delete(user);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not admin");
    }

    @GetMapping("/api/user/loggedin")
    public User getLoggedIn(HttpServletRequest request){
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


    /**
     *
     * @param key key generated by the software to verify email
     * @param session current HTTPSession
     * @return the newly added user if verification is successful
     */
    @GetMapping("/verify/{key}")
    public void verifyKeyForUser(@PathVariable("key") String key,
                                 HttpSession session,
                                 HttpServletResponse response,
                                 HttpServletRequest request) throws IOException {
        String token = request.getHeader("Authorization");
        User user = (User) session.getAttribute(token);
        if(user == null){
            User newlyVerifiedUser = (User) session.getAttribute(key);
            userService.save(newlyVerifiedUser);
            try {
                response.sendRedirect("/login");
                return;
            }catch(IOException e){
                e.printStackTrace();
                return;
            }

        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not admin");
    }
}
