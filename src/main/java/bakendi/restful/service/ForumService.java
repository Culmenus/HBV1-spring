package bakendi.restful.service;

import bakendi.restful.persistence.entities.Forum;
import java.util.List;
import java.util.Optional;

public interface ForumService {
    // bæta við fleirum seinna..?

    Forum save(Forum forum);
    void delete(Forum forum);
    Forum findByName(String name);
    List<Forum> findAll();
    Forum findByID(long ID);

    Forum findByCourseId(String courseId);
}
