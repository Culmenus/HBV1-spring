package bakendi.restful.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import bakendi.restful.persistence.entities.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

    Thread save(Thread thread);
    void delete(Thread thread);

    Thread findByTitle(String title);
    List<Thread> findAll();
    Thread findByID(long ID);

}
