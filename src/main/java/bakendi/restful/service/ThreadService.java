package bakendi.restful.service;

import bakendi.restful.persistence.entities.Forum;
import bakendi.restful.persistence.entities.Thread;

import java.util.List;

public interface ThreadService {
    Thread save(Thread thread);
    void delete(Thread thread);
    Thread findByTitle(String title);
    List<Thread> findAll();
    Thread findByID(long ID);
}
