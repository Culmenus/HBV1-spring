package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.repositories.ForumRepository;
import bakendi.restful.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumServiceImplementation implements ForumService {
    private ForumRepository forumRepository;

    @Autowired
    ForumServiceImplementation(ForumRepository forumRepository) {
        //this.forumRepository = new ForumRepository(); // ath
        this.forumRepository = forumRepository;
    }


    @Override
    public Forum findByTitle(String title) {
        return null;
    }

    @Override
    public List<Forum> findAll() {
        return null;
    }

    @Override
    public Forum findByID(long ID) {
        return null;
    }

    @Override
    public Forum save(Forum forum) {
        return null;
    }

    @Override
    public void delete(Forum forum) {

    }
}
