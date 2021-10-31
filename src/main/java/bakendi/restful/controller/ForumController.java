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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
public class ForumController {
    private ForumService forumService;
    private ThreadService threadService;
    private UserService userService;

    @Autowired
    public ForumController(ForumService forumService, ThreadService threadService, UserService userService) {
        this.threadService = threadService;
        this.forumService = forumService;
        this.userService = userService;
    }

    @PostMapping("/api/forums")
    public Forum forumsPOST(Forum forum, BindingResult result, HttpServletResponse response) throws IOException {
        if(result.hasErrors()) {
            response.sendRedirect("/forums");
            return forum;
        }

        // add to favourites? what do here?
        return forum;
    }

    @GetMapping("/api/forum/{id}")
    public Forum findForumById(@PathVariable("id") long id) {
        return forumService.findByID(id);
    }

    @PostMapping("/api/forum/{id}")
    public Thread createThreadPOST(Thread thread, @PathVariable("id") long id) {
        //ndk: þurfum við að auðkenna notandann hér? eða er það gert á öðru leveli?
        //ndk: vantar að tengja user við thread?
        Forum forum = forumService.findByID(id);
        forum.addThread(thread);
        return thread;
    }

    @PatchMapping("/api/forum/{id}") //react sendir thread gögnin
    public Thread updateThread(@PathVariable("id") long id, HttpSession session, Thread thread){
        threadService.save(thread);
        return thread;
    }
    @DeleteMapping("/api/forum/{id}")
    public Thread deleteThread(@PathVariable("id") long id, HttpSession session, Thread thread){
        threadService.delete(thread);
        return thread;
    }

    @GetMapping("/error")
    public void ErrorHandler(HttpServletResponse response) throws IOException {
        response.sendError(0, "Error occurred"  );
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
