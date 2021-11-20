package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.service.MessageService;
import bakendi.restful.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class MessageController {
    private MessageService messageService;
    private ThreadService threadService;

    @Autowired
    public MessageController(MessageService messageService, ThreadService threadService) {
        this.messageService = messageService;
        this.threadService = threadService;
    }

    @GetMapping("/api/message/{messageId}")
    public Message getMessage(@PathVariable("messageId") long id) {
        return messageService.findById(id);
    }

    @PostMapping("/api/thread/{threadId}")
    public Message createMessage(@RequestBody Message message, @PathVariable("threadId") long id, HttpSession session) {
        Thread thread = threadService.findByID(id);
        if (thread != null) {
            // breyta thessu i token utfærslu? also how?
            User user = (User) session.getAttribute("loggedInUser");
            if (user != null) {
                message.setSentBy(user);
            }
            message.setCreatedAt(new Date());
            thread.addMsg(message);
            message.setThread(thread);

            messageService.save(message);
            threadService.save(thread);
            return messageService.findById(message.getID());
        }

        return null;
    }



}
