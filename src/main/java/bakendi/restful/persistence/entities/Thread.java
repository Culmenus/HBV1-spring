package bakendi.restful.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "threads")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String title;
    private String description;
    private Date lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    private Forum forum;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public Thread(String title, String description, List<Message> messages, Date lastUpdated) {
        this.title = title;
        this.description = description;
        this.messages = messages;
        this.lastUpdated = lastUpdated;
    }

    public Thread() {

    }
    // dha: held við ættum að breyta long yfir í Message
    public void addMsg(long msgId) {
        // todo
    }

    // dha: held við ættum að breyta long yfir í Message
    public void removeMsg(long msgId) {
        // todo
    }

    public long getID() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
