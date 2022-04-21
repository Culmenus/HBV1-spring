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
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class ForumController {
    private final ForumService forumService;
    private final ThreadService threadService;
    private final UserService userService;
    private final UserController userController;

    @Autowired
    public ForumController(ForumService forumService, ThreadService threadService, UserService userService, UserController userController) {
        this.threadService = threadService;
        this.forumService = forumService;
        this.userService = userService;
        this.userController = userController;
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

    @PostMapping("/api/favorite-forums/{forumId}") //add to favorites
    public Set<Forum> addToFavorites(@PathVariable("forumId") long forumId, HttpServletRequest request){
        User user = userController.getLoggedIn(request);
        Forum forum = forumService.findByID(forumId);
        user.addToFavorites(forum);
        userService.save(user);
        return user.getFavoriteForums();
    }
    @PostMapping("/api/delete-favorite-forums/{forumId}") //add to favorites
    public Set<Forum> deleteFromFavorites(@PathVariable("forumId") long forumId, HttpServletRequest request){
        User user = userController.getLoggedIn(request);
        Forum forum = forumService.findByID(forumId);
        user.removeFromFavorites(forum);
        userService.save(user);

        return user.getFavoriteForums();
    }

    @GetMapping("/api/favorite-forums") //get favorite forums
    public Set<Forum> getFavorites(HttpServletRequest request){
        User user = userController.getLoggedIn(request);

        return user.getFavoriteForums();
    }

    @GetMapping("/api/forum") //get all forums
    public List<Forum> getAllForums(){
        return forumService.findAll();
    }

    @PatchMapping("/api/forum/{forumId}") //react sendir thread gögnin
    public Forum updateForum(@RequestBody Forum forum, @PathVariable("forumId") long id, HttpSession session){
        Forum old = forumService.findByID(id);
        if (old != null) {
            old.setCourseId(forum.getCourseId());
            old.setName(forum.getName());

            forumService.save(old);
        }

        return old;
    }

    @DeleteMapping("/api/forum/{forumId}")
    public boolean deleteForum(@PathVariable("forumId") long id) {
        Forum old = forumService.findByID(id);
        if (old != null) {
            forumService.delete(old);
            return true;
        } else
            return false;
    }

}
