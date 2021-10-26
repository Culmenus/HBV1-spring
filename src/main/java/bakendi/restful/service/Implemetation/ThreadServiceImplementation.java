package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ThreadServiceImplementation {
    private ThreadRepository threadRepository;

    @Autowired
    public ThreadServiceImplementation(ThreadRepository threadRepository) {
        this.threadRepository = new ThreadRepository()
    }

    @Override
    public List<Thread> findAll() {
        return threadRepository.findAll();
    }
}

