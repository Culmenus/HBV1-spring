package bakendi.restful.persistence.repositories;

import bakendi.restful.persistence.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    Forum save(Forum forum);
    void delete(Forum forum);
    Forum findByName(String name);
    List<Forum> findAll();
    Forum findByID(long ID);

}
