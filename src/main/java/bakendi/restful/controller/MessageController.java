package bakendi.restful.controller;

import bakendi.restful.model.MessageDto;
import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.entities.Thread;
import bakendi.restful.service.MessageService;
import bakendi.restful.service.ThreadService;
import bakendi.restful.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class MessageController {
    private MessageService messageService;
    private ThreadService threadService;
    private UserService userService;
    @Autowired
    public MessageController(MessageService messageService, ThreadService threadService, UserService userService) {
        this.messageService = messageService;
        this.threadService = threadService;
        this.userService = userService;
    }

    @GetMapping("/api/message")
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }

    @GetMapping("/api/message/{messageId}")
    public Message getMessage(@PathVariable("messageId") long id) {
        return messageService.findById(id);
    }

    @MessageMapping("/thread/{threadId}/send")
    @SendTo("/thread/{threadId}/get")
    public MessageDto interceptMessage(MessageDto msg) throws Exception{
        System.out.println("Get!");
        msg.setCreatedAt(new Date());
        return msg;
    }

    @PostMapping("/api/thread/{threadId}")
    public Message createMessage(@RequestBody MessageDto message, @PathVariable("threadId") long id) {
        Thread thread = threadService.findByID(id);
        User user = userService.findById(message.getUserID());
        if (thread != null) {
            Message newMessage = new Message(user, message.getMessage(), message.isEdited());
            message.setCreatedAt(new Date());
            thread.addMsg(newMessage);
            newMessage.setThread(thread);

            messageService.save(newMessage);
            threadService.save(thread);
            return messageService.findById(newMessage.getID());
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
