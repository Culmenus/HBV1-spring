package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.service.MessageService;
import bakendi.restful.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    private MessageService messageService;
    private ThreadService threadService;

    @Autowired
    public MessageController(MessageService messageService, ThreadService threadService) {
        this.messageService = messageService;
        this.threadService = threadService;
    }

    @GetMapping("/api/message")
    public List<Message> getAllMessages() {
        return messageService.findAll();
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
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Thread not found");
    }

    @PatchMapping("/api/message/{messageId}")
    public Message updateMessage(@RequestBody Message message, @PathVariable("messageId") long id) {
        Message old = messageService.findById(id);
        old.setMessage(message.getMessage());
        old.setEdited(true);
        messageService.save(old);
        return messageService.findById(old.getID());
    }

    @DeleteMapping("/api/message/{messageId}")
    public boolean deleteMessage(@PathVariable("messageId") long id) {
        Message msg = messageService.findById(id);
        if (msg != null) {
            messageService.delete(msg);
            return true;
        }
        return false;
    }



}
