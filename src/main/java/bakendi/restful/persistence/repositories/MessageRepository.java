package bakendi.restful.persistence.repositories;

import bakendi.restful.persistence.entities.Message;
import bakendi.restful.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message save(Message message);
    //Message update(Message message);
    void delete(Message message);
    List<Message> findByMessage(String message);
    List<Message> findAll();
    Message findById(long id);
}
