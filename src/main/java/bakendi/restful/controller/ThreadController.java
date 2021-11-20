package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ThreadController {
    private final ForumService forumService;
    private final ThreadService threadService;

    @Autowired
    public ThreadController(ForumService forumService, ThreadService threadService) {
        this.threadService = threadService;
        this.forumService = forumService;
    }

    @GetMapping("/api/thread/{threadId}")
    public Thread getThread(@PathVariable("threadId") long id) {
        return threadService.findByID(id);
    }

    @PostMapping("/api/forum/{forumId}")
    public Thread createThread(@RequestBody Thread thread, @PathVariable("forumId") long id, HttpSession session) {
        Forum forum = forumService.findByID(id);
        // breyta thessu i token utfærslu? also how?
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

    @PatchMapping("/api/thread/{threadId}") //react sendir thread gögnin
    public Thread updateThread(@RequestBody Thread thread, @PathVariable("threadId") long id, HttpSession session){
        Thread old = threadService.findByID(id);
        if (old != null) {
            old.setTitle(thread.getTitle());
            old.setDescription(thread.getDescription());
            old.setLastUpdated(thread.getLastUpdated());

            threadService.save(old);
        }

        return old;
    }

    @DeleteMapping("/api/thread/{threadId}")
    public Thread deleteThread(@PathVariable("threadId") long id, HttpSession session){

        Thread old = threadService.findByID(id);
        if (old != null) {
            System.out.println(old);
            threadService.delete(old);
        }

        return old;
    }
}
