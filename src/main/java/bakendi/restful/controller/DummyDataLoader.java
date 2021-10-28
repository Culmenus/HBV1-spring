package bakendi.restful.controller;

import bakendi.restful.persistence.entities.*;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DummyDataLoader {
    private ForumService forumService;
    private ThreadService threadService;
    private UserService userService;

    @Autowired
    public DummyDataLoader(ForumService forumService, ThreadService threadService, UserService userService) {
        this.forumService = forumService;
        this.threadService = threadService;
        this.userService = userService;
    }

    @PostMapping("/initdummy")
    public void forums() {
        userService.save(new User("Danni", "pword", "user@user.is", UserRole.ROLE_USER));
        userService.save(new User("Jon", "pword2", "admin@user.is", UserRole.ROLE_ADMIN));
        forumService.save(new Forum("TÖL104G", "Stærðfræðimynstur"));
        forumService.save(new Forum("9+10", "21"));
        forumService.save(new Forum("TÖL101G", "Tölvunarfræði 1"));
        threadService.save(new Thread());
        threadService.save(new Thread());
    }
}