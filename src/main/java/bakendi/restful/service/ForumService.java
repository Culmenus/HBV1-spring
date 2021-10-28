package bakendi.restful.service;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.User;
import bakendi.restful.persistence.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface ForumService {
    // bæta við fleirum seinna...

    Forum save(Forum forum);
    void delete(Forum forum);
    Forum findByName(String name);
    List<Forum> findAll();
    Forum findByID(long ID);
}
