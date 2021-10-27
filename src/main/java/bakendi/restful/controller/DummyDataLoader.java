package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.service.ForumService;
import bakendi.restful.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DummyDataLoader {
    private ForumService forumService;
    private ThreadService threadService;

    @Autowired
    public DummyDataLoader(ForumService forumService, ThreadService threadService) {
        this.forumService = forumService;
        this.threadService = threadService;
    }

    @GetMapping("/initdummy")
    public void forums() {
        forumService.save(new Forum("TÖL104G", "Stærðfræðimynstur"));
        forumService.save(new Forum("9+10", "21"));
        forumService.save(new Forum("TÖL101G", "Tölvunarfræði 1"));
        threadService.save(new Thread());
    }
}