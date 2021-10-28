package bakendi.restful.controller;


import bakendi.restful.security.JWTAuthorizationFilter;
import bakendi.restful.service.ForumService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Formattable;
import java.util.List;

@RestController
public class HomeController {
    ForumService forumService;
    UserService userService;

    @Autowired
    public HomeController(ForumService forumService, UserService userService){
        this.forumService = forumService;
        this.userService = userService;
    }

    @GetMapping("/")
    public void HomeController(HttpServletResponse response) throws IOException {

        // logged in logic?
        //response.sendRedirect("/login");

        // for now
        response.sendRedirect("/forums");

    }

    @GetMapping("/error")
    public void ErrorHandler(HttpServletResponse response) throws IOException {
        response.sendError(0, "Error occurred"  );
    }


    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    //TODO: eyda tessu shitti
                    .antMatchers(HttpMethod.POST, "/initdummy").permitAll()
                    .anyRequest().authenticated();
        }
    }

    @PostMapping("/favorite-forum/{id}") //add to favorites
    public List<Forum> addToFavorites(@PathVariable("id") long id, HttpSession session){
        Forum forum = forumService.findByID(id);
        User user = (User) session.getAttribute("user");
        user.addToFavorites(forum);
        userService.save(user);
        return user.getFavoriteForums();
    }

    @GetMapping("/favorite-forums") //get favorite forums
    public List<Forum> getFavorites(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user.getFavoriteForums();
    }

    @GetMapping("/forums") //get all forums
    public List<Forum> getAll(){

        return forumService.findAll();
    }
}
