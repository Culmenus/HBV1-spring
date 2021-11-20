package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class ForumController {
    private final ForumService forumService;
    private final ThreadService threadService;
    private final UserService userService;

    @Autowired
    public ForumController(ForumService forumService, ThreadService threadService, UserService userService) {
        this.threadService = threadService;
        this.forumService = forumService;
        this.userService = userService;
    }

    @PostMapping("/api/forum")
    public Forum createForum(@RequestBody Forum forum) {
        // sanitize/validate forum?
        forumService.save(forum);
        return forumService.findByCourseId(forum.getCourseId());
    }

    @GetMapping("/api/forum/{id}")
    public Forum findForumById(@PathVariable("id") long id) {
        return forumService.findByID(id);
    }

    @GetMapping("/error")
    public void ErrorHandler(HttpServletResponse response) throws IOException {
        response.sendError(0, "Error occurred"  );
    }

    @PostMapping("/favorite-forum/{id}") //add to favorites
    public List<Forum> addToFavorites(@PathVariable("id") long id, HttpSession session){
        Forum forum = forumService.findByID(id);
        User user = (User) session.getAttribute("loggedInUser");
        user.addToFavorites(forum);
        userService.save(user);
        return user.getFavoriteForums();
    }

    @GetMapping("/favorite-forums") //get favorite forums
    public List<Forum> getFavorites(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return user.getFavoriteForums();
    }

    @GetMapping("/api/forum") //get all forums
    public List<Forum> getAllForums(){
        return forumService.findAll();
    }

}
