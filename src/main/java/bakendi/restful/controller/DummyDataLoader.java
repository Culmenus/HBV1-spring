package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.UserRole;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        User user1 = new User("Danni", "pword", "user@user.is", UserRole.ROLE_USER);
        User user2 = new User("Jon", "pword2", "admin@user.is", UserRole.ROLE_ADMIN);
        userService.save(user1);
        userService.save(user2);
        Forum forum1 = new Forum("TÖL104G", "Stærðfræðimynstur");
        forumService.save(forum1);
        forumService.save(new Forum("9+10", "21"));
        forumService.save(new Forum("TÖL101G", "Tölvunarfræði 1"));
        threadService.save(new Thread(user1));
        threadService.save(new Thread(forum1, "profid madur", "pls er eg sa eini", user1));
    }
}