package bakendi.restful.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    /*
    ForumService forumService;
    UserService userService;

    @Autowired
    public HomeController(ForumService forumService, UserService userService){
        this.forumService = forumService;
        this.userService = userService;
    }

    @RequestMapping(value = "/")
    public void HomeController(HttpServletResponse response) throws IOException {

        // logged in logic?
        //response.sendRedirect("/login");

        // for now


    }

     */

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }


}
