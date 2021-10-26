package bakendi.restful.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "forums")
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String courseId; // sbr TÖL025M eða eitthvað
    private String name;
    private String description;
    private List<long> threads;

    // ásett ráð að hafa ekki descr í constructor
    public Forum(long id, String courseId, String courseName) {
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public void addThread(long thrdId) {
        // todo
    }

    public void removeThread(long thrdId) {
        // todo
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<long> getThreads() {
        return threads;
    }

    public void setThreads(List<long> threads) {
        this.threads = threads;
    }
}
