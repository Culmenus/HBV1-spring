package bakendi.restful.persistence.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

// TODO VENSLA SAMANA ENTITY, SBR. ONE TO MANY OSFRV
@Entity
@Table(name = "threads")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private List<long> messages;
    private Date lastUpdated;

    public Thread(long id, String title, String description, List<long> messages, Date lastUpdated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.messages = messages;
        this.lastUpdated = lastUpdated;
    }

    public void addMsg(long msgId) {
        // todo
    }

    public void removeMsg(long msgId) {
        // todo
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<long> getMessages() {
        return messages;
    }

    public void setMessages(List<long> messages) {
        this.messages = messages;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
