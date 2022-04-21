package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class ThreadController {
    private final ForumService forumService;
    private final ThreadService threadService;
    private final UserController userController;
    @Autowired
    public ThreadController(ForumService forumService, ThreadService threadService, UserController userController) {
        this.threadService = threadService;
        this.forumService = forumService;
        this.userController = userController;
    }
    @GetMapping("/api/thread")
    public List<Thread> getAllThreads() {
        return threadService.findAll();
    }

    @GetMapping("/api/thread/{threadId}")
    public Thread getThread(@PathVariable("threadId") long id) {
        return threadService.findByID(id);
    }

    @PostMapping("/api/forum/{forumId}")
    public Thread createThread(@RequestBody Thread thread, @PathVariable("forumId") long id, HttpServletRequest request) {
        Forum forum = forumService.findByID(id);
        // breyta thessu i token utfærslu? also how?
        User user = userController.getLoggedIn(request);

        if (user != null) {
            thread.setCreator(user); 
        }

        thread.setLastUpdated(new Date());
        forum.addThread(thread);
        thread.setForum(forum);

        threadService.save(thread);
        forumService.save(forum);
        return threadService.findByID(thread.getID());
    }

    @PatchMapping("/api/thread/{threadId}") //react sendir thread gögnin
    public Thread updateThread(@PathVariable("newTitle") String newTitle,
                               @PathVariable("newDescription")String newDesc,
                               @PathVariable("threadId") long id){
        Thread old = threadService.findByID(id);
        if (old != null) {
            old.setTitle(newTitle);
            old.setDescription(newDesc);
            old.setLastUpdated(new Date());

            threadService.save(old);
        }

        return old;
    }

    @DeleteMapping("/api/thread/{threadId}")
    public boolean deleteThread(@PathVariable("threadId") long id){

        Thread old = threadService.findByID(id);
        if (old != null) {
            threadService.delete(old);
            return true;
        }
        return false;
    }
}
