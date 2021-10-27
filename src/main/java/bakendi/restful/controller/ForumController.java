package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;

@RestController
public class ForumController {
    private ForumService forumService;

    @Autowired
    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/forums")
    public List<Forum> forumsGET() {
        return forumService.findAll();
    }


    @GetMapping("/forum/{id}")
    public Forum forumByIdGET(@PathVariable("id") long id) {
        return forumService.findByID(id);
    }

    @PostMapping("/forum/{id}")
    public Thread createThreadPOST(Thread thread, @PathVariable("id") long id) {
        Forum forum = forumService.findByID(id);
        forum.addThread(thread);
        return thread;
    }

    @PostMapping("/forums") //add to favorites
    public List<Forum> addToFavorites(User user, Forum forum){
        List<Forum > userForums = forumService.saveToFavorites(user, forum);
        return userForums;
    }
}
