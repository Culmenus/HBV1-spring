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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Formattable;
import java.util.List;

@Controller
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
}
