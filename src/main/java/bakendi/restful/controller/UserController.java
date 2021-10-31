package bakendi.restful.controller;


import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.security.JWTUtils;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {
    UserService userService;
    JWTUtils jwtUtils;
    @Autowired
    public UserController(UserService userService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/api/login")
    public String login(@RequestParam("user") String username, @RequestParam("password") String pwd, HttpSession session) {
        try {
            User user = userService.findByUsername(username);
            String token = userService.getTokenForUser(user,pwd);
            if (token != null) {
                session.setAttribute("loggedInUser",user);
                return token;
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
    User signup(@RequestBody User user) {
       return userService.createNewUser(user);
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
    public User update(@RequestBody User changedUser, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null && user.getID() == changedUser.getID())
            return userService.update(changedUser);
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user ID does not match logged in user");
    }

    @DeleteMapping("/api/user/delete")
    public void delete(HttpSession session) {
        // ensure admin?
        User user= (User) session.getAttribute("loggedInUser");
        if(user.getUserRole() == UserRole.ROLE_ADMIN){
            userService.delete(user);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not admin");
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
