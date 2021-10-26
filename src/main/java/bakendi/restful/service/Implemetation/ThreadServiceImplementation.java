package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.repositories.ThreadRepository;
import bakendi.restful.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadServiceImplementation implements ThreadService {
    private ThreadRepository threadRepository;

    @Autowired
    public ThreadServiceImplementation(ThreadRepository threadRepository) {
        //this.threadRepository = new ThreadRepository();
        this.threadRepository = threadRepository;
    }

    //@Override
    public List<Thread> findAll() {
        return threadRepository.findAll();
    }
}

