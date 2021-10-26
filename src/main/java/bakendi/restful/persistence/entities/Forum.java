package bakendi.restful.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forums")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String courseId; // sbr TÖL025M eða eitthvað
    private String name;
    private String description;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Thread> threads = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // ásett ráð að hafa ekki descr í constructor
    public Forum(String courseId, String courseName) {
        this.courseId = courseId;
        this.name = courseName;
    }

    // dha: intellij ask for this i give
    public Forum() {

    }

    public void addThread(long thrdId) {
        // todo
    }

    public void removeThread(long thrdId) {
        // todo
    }

    public long getID() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
