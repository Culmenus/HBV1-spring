package bakendi.restful.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    // hef util, til að hafa sek
    private Date createdAt;
    private String message;
    private boolean isEdited;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Thread thread;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User sentBy;

    public Message(Date createdAt, User sentBy, String message, boolean isEdited) {
        this.createdAt = createdAt;
        this.sentBy = sentBy;
        this.message = message;
        this.isEdited = isEdited;
    }

    public Message() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public User getSentBy() {
        return sentBy;
    }

    public void setSentBy(User sentBy) {
        this.sentBy = sentBy;
    }
}
