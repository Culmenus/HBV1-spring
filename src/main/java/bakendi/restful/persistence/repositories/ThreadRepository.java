package bakendi.restful.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

    Thread save(Thread thread);
    void delete(Thread thread);

    List<Thread> findAll();
    List<Thread> findByTitle(String title);
    Thread findByID(long id);

}
