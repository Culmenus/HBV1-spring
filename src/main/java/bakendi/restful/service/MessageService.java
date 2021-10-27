package bakendi.restful.service;

import bakendi.restful.persistence.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message save(Message message);
    //Message update(Message message);
    void delete(Message message);
    List<Message> findByMessage(String message);
    List<Message> findAll();
    Message findById(long id);
}
