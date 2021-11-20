package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.repositories.ForumRepository;
import bakendi.restful.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImplementation implements ForumService {
    private ForumRepository forumRepository;

    @Autowired
    ForumServiceImplementation(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public Forum findByName(String name) {
        return forumRepository.findByName(name);
    }

    @Override
    public List<Forum> findAll() {
        return forumRepository.findAll();
    }

    @Override
    public Forum findByID(long ID) {
        return forumRepository.findByID(ID);
    }

    @Override
    public Forum findByCourseId(String courseId) {
        return forumRepository.findByCourseId(courseId);
    }

    @Override
    public Forum save(Forum forum) {
        return forumRepository.save(forum);
    }

    @Override
    public void delete(Forum forum) {
        forumRepository.delete(forum);
    }
}
