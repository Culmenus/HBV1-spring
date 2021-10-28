package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;

@RestController
public class ForumController {
    private ForumService forumService;
    private UserService userService;
    @Autowired
    public ForumController(ForumService forumService, UserService userService) {

        this.forumService = forumService;
        this.userService = userService;
    }

    @GetMapping("/forums")
    public List<Forum> forumsGET() {
        return forumService.findAll();
    }


    @GetMapping("/forum/{id}")
    public Forum forumByIdGET(@PathVariable("id") long id) {
        return forumService.findByID(id);
    }

    @PostMapping("/forum/{id}")//senda json í body í react.
    public Thread createThreadPOST(Thread thread,HttpSession session, @PathVariable("id") long id) {
        Forum forum = forumService.findByID(id);
        forum.addThread(thread);
        return thread;
    }

    @PostMapping("/forum/{id}") //add to favorites
    public List<Forum> addToFavorites(@PathVariable("id") long id, HttpSession session){
        Forum forum = forumService.findByID(id);
        User user = (User) session.getAttribute("user");
        user.addToFavorites(forum);
        userService.save(user);
        return user.getFavoriteForums();
    }
}
