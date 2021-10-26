package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ForumServiceImplementation {
    private ForumRepository forumRepository;

    @Autowired ForumServiceImplementation(ForumRepository forumRepository) {
        this.forumRepository = new ForumRepository(); // ath
    }

    @Override
    public List<Forum> findAll() {
        return forumRepository.findAll();
    }
}
