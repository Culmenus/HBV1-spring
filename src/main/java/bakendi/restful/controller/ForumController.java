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

    @GetMapping("/api/forum/{id}")
    public Forum findForumById(@PathVariable("id") long id) {
        return forumService.findByID(id);
    }

    @PostMapping("/api/forum/{id}")
    public Thread createThread(@RequestBody Thread thread, @PathVariable("id") long id, HttpSession session) {
        Forum forum = forumService.findByID(id);
        // breyta thessu i token utfærslu?
        User user = (User) session.getAttribute("loggedInUser");
        
        if (user != null) {
            thread.setCreator(user);   /// gera thetta thegar httpsession er komid a hreint
        }
        forum.addThread(thread);
        thread.setForum(forum);

        threadService.save(thread);
        forumService.save(forum);
        return threadService.findByID(thread.getID());
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

    @GetMapping("/api/forums") //get all forums
    public List<Forum> getAll(HttpServletRequest request){
        System.out.println(request.getContextPath());
        return forumService.findAll();
    }

}
