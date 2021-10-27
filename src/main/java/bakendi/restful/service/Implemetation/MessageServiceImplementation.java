package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.repositories.MessageRepository;
import bakendi.restful.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService {
    private MessageRepository messageRepository;

    @Autowired
    MessageServiceImplementation(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public List<Message> findByMessage(String message) {
        return messageRepository.findByMessage(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(long id) {
        return messageRepository.findById(id);
    }
}
