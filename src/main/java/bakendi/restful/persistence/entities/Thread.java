package bakendi.restful.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Forum forum;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    public Thread(String title, String description, List<Message> messages, Date lastUpdated, User creator) {
        this.title = title;
        this.description = description;
        this.messages = messages;
        this.lastUpdated = lastUpdated;
        this.creator = creator;
    }

    public Thread(Forum forum, String title, String description, User creator) {
        this.forum = forum;
        this.title = title;
        this.description = description;
        this.creator = creator;
    }

    public Thread(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Thread(User creator) {
        this.creator = creator;
    }

    public Thread() {

    }

    public void addMsg(Message message) {
        this.messages.add(message);
        System.out.println(this.messages);
    }

    public void removeMsg(Message message) {
        this.messages.remove(message);
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) { this.creator = creator; }

    public Forum getForum() {
        return forum;
    }

    public void setForum( Forum forum ) {
        this.forum = forum;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", forum=" + forum +
                ", messages=" + messages +
                ", creator=" + creator +
                '}';
    }
}
