package bakendi.restful.controller;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/message/{messageId}")
    public Message getMessage(@PathVariable("messageId") long id) {
        return messageService.findById(id);
    }


}
