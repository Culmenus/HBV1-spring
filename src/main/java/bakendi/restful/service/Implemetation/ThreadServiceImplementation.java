package bakendi.restful.service.Implemetation;

import bakendi.restful.persistence.entities.Thread;

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
        this.threadRepository = threadRepository;
    }

    @Override
    public Thread findByTitle(String title) { return threadRepository.findByTitle(title); }

    @Override
    public List<Thread> findAll() {
        return threadRepository.findAll();
    }

    @Override
    public Thread findByID(long ID) { return threadRepository.findByID(ID); }

    @Override
    public Thread save(Thread thread) { return threadRepository.save(thread); }

    @Override
    public void delete(Thread thread) { threadRepository.delete(thread); }
}

