package bakendi.restful.persistence.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

// TODO VENSLA SAMANA ENTITY, SBR. ONE TO MANY OSFRV
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // hef util, til að hafa sek
    private Date createdAt;
    private long sentById;
    private String message;
    private boolean isEdited;

    public Message(long id, Date createdAt, long sentById, String message, boolean isEdited) {
        this.id = id;
        this.createdAt = createdAt;
        this.sentById = sentById;
        this.message = message;
        this.isEdited = isEdited;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getSentById() {
        return sentById;
    }

    public void setSentById(long sentById) {
        this.sentById = sentById;
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
}
