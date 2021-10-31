package bakendi.restful.controller;

import bakendi.restful.service.ForumService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
