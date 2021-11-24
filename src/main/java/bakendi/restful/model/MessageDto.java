package bakendi.restful.model;

import java.util.Date;

//simple message for quick sending through websocket.
public class MessageDto {
    private String message;
    private boolean isEdited;
    private long userID;
    private String username;
    private Date createdAt;
    public MessageDto(){

    };
    public MessageDto(String message, boolean isEdited, long userID,String username){
        this.message = message;
        this.isEdited = isEdited;
        this.userID = userID;
        this.username = username;
    }

    public long getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
